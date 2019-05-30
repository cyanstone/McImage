package com.smallsoho.mcplugin.image.webp

import com.google.webp.libwebp
import com.smallsoho.mcplugin.image.Const
import com.smallsoho.mcplugin.image.Config
import com.smallsoho.mcplugin.image.utils.*
import com.smallsoho.mcplugin.image.webp.webpapi.WebpConversionSettings
import org.gradle.api.Project
import java.io.File

class WebpUtils {

    companion object {
        private const val VERSION_SUPPORT_WEBP = 14 //api>=14设设备支持webp
        private const val VERSION_SUPPORT_TRANSPARENT_WEBP = 18 //api>=18支持透明通道
        private const val TAG = "Webp"
        var pngCount = 0
        var webpCount = 0

        private fun isPNGConvertSupported(project: Project): Boolean {
            return AndroidUtil.getMinSdkVersion(project) >= VERSION_SUPPORT_WEBP
        }

        private fun formatWebp(imgFile: File): Long {
            if (ImageUtil.isImage(imgFile)) {
                var webpFile = File("${imgFile.path.substring(0, imgFile.path.lastIndexOf("."))}.webp")
                //Tools.cmd("cwebp", "${imgFile.path} -o ${webpFile.path} -q 75 -m 6 -quiet")
                webpFile = WebpConvertHelper.convert(imgFile, WebpConversionSettings())
                if(webpFile == null || webpFile.length() == 0L) {
                    throw Exception("Webp file can not be null or 0 length!")
                }
                if (webpFile.length() < imgFile.length()) {
                    LogUtil.log(TAG, imgFile.path, imgFile.length().toString(), webpFile.length().toString())
                    if (imgFile.exists()) {
                        imgFile.delete()
                    }
                    webpCount++
                    return webpFile.length()
                } else {
                    //如果webp的大的话就抛弃
                    if (webpFile.exists()) {
                        webpFile.delete()
                    }
                    println("cwebp size[${webpFile.name}] > imgFile size[${imgFile.name}]")
                    val size = CompressUtil.compressImg(imgFile)
                    pngCount++
                    return size
                }
            }
            return imgFile.length()
        }

        fun securityFormatWebp(imgFile: File, config: Config, project: Project): Long {
            if(!isPNGConvertSupported(project)) {
                throw Exception("minSDK < 14, Webp is not Support! Please choose other optimize way!")
            }
            var size = 0L
            if (ImageUtil.isImage(imgFile)) {
                if(config.isSupportAlphaWebp) {
                    size = formatWebp(imgFile)
                } else {
                    if(imgFile.name.endsWith(Const.JPG) || imgFile.name.endsWith(Const.JPEG)) {
                        //jpg
                        size = formatWebp(imgFile)
                    } else if(imgFile.name.endsWith(Const.PNG) ){
                        //png
                        if(!ImageUtil.isAlphaPNG(imgFile)) {
                            //不包含透明通道
                            size = formatWebp(imgFile)
                        } else {
                            //包含透明通道的png，进行压缩
                            size = CompressUtil.compressImg(imgFile)
                            pngCount++
                        }
                    }
                }
            }
            return size

        }

    }

}