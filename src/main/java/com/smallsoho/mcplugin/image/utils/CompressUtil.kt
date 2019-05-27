package com.smallsoho.mcplugin.image.utils

import java.io.File

class CompressUtil {

    companion object {
        private const val TAG = "Compress"
        fun compressImg(imgFile: File) {
            if (!ImageUtil.isImage(imgFile)) {
                return
            }

            val oldSize = imgFile.length()
            var newSize = 0L
            if (ImageUtil.isJPG(imgFile)) {
                var tempFilePath: String = "${imgFile.path.substring(0, imgFile.path.lastIndexOf("."))}_temp" +
                        "${imgFile.path.substring(imgFile.path.lastIndexOf("."))}"
                Tools.cmd("guetzli", "${imgFile.path} ${tempFilePath}")
                val tempFile: File = File(tempFilePath)
                newSize = tempFile.length()
                println("newSize = ${newSize}")
                if (newSize < oldSize) {
                    var imgFileName: String = imgFile.path
                    if (imgFile.exists()) {
                        imgFile.delete()
                    }
                    var newFile: File = File(imgFileName)
                    tempFile.renameTo(newFile)
                } else {
                    if (tempFile.exists()) {
                        tempFile.delete()
                    }
                }

            } else {
                Tools.cmd("pngquant", "--skip-if-larger --speed 1 --nofs --strip --force --output ${imgFile.path} -- ${imgFile.path}")
                newSize = imgFile.length()
            }

            LogUtil.log(TAG, imgFile.path, oldSize.toString(), newSize.toString())
        }
    }

}