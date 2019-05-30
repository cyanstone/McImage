package com.smallsoho.mcplugin.image.webp;

import com.smallsoho.mcplugin.image.utils.ImageUtil;
import com.smallsoho.mcplugin.image.webp.webpapi.WebpConversionSettings;
import com.smallsoho.mcplugin.image.webp.webpapi.WebpImageWriterSpi;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import java.awt.image.BufferedImage;
import java.io.*;

public class WebpConvertHelper {
    private static File sourceFile;
    private static long sourceFileSize;
    public static byte[] encoded;
    public static long saved;
    public static File convert(File sourceImgFile, WebpConversionSettings settings) {
        sourceFile = sourceImgFile;
        sourceFileSize = sourceImgFile.length();
        try {
            InputStream stream = new BufferedInputStream(new FileInputStream(sourceImgFile));
            BufferedImage image = ImageIO.read(stream);
            stream.close();

            if(convert(image, settings)) {
                return apply();
            }
            return sourceImgFile;
        }
        catch (IOException e) {
            return sourceImgFile;
        }
    }

    public static boolean convert(@NotNull BufferedImage image, @NotNull WebpConversionSettings settings) {
        try {
            // See if we find an alpha channel in this image and if so, return null
            if (settings.skipTransparentImages) {
                String name = sourceFile.getName();
                if (name.endsWith(".png") || name.endsWith(".gif")) {
                    if (ImageUtil.Companion.isNonOpaque(image)) {
                        return false;
                    }
                }
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int)sourceFileSize);

            ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(image);
            if (!WebpImageWriterSpi.canWriteImage(type)) {
                return false;
            }

            WebpImageWriterSpi.writeImage(image, byteArrayOutputStream, settings.lossless, settings.quality);
            encoded = byteArrayOutputStream.toByteArray();
            saved = sourceFileSize - encoded.length;
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public static String getNameWithoutExtension(File file) {
        String name = file.getName();
        int index = name.lastIndexOf(".");
        if (index < 0) {
            if (name != null) {
                return name;
            }
        } else {
            String result = name.substring(0, index);
            if (result != null) {
                return result;
            }
        }

        throw new IllegalStateException("@NotNull method getNameWithoutExtension must not return null");
    }


    public static File apply() throws IOException {
        String folder = sourceFile.getParent();
        File output = new File(folder, getNameWithoutExtension(sourceFile) + ".webp");
        try (OutputStream fos = new BufferedOutputStream(new FileOutputStream(output))) {
            fos.write(encoded);
        }
        if(sourceFile.exists()) {
            //sourceFile.delete();
        }
        return output;
    }

}
