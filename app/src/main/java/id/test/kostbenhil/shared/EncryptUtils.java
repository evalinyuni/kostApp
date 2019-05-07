package id.test.kostbenhil.shared;

import android.content.Context;
import id.test.kostbenhil.R;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Locale;

public class EncryptUtils {

//    public static String encrypt(Context mContext, String original) {
//        String result = "";
//        try {
//            PublicKey pubKeyFromDer = getPublicFromDer(mContext);
//            PublicKey pubKeyFromPem = getPublicFromPem(mContext);
//            PublicKey pubKeyFromMod = getPublicFromModulus();
//
//            final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, pubKeyFromPem);
//            byte[] textB = cipher.doFinal(original.getBytes("UTF-8"));
//            result = new String(Base64Coder.encode(textB));
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//        return result;
//    }

//    private static PublicKey getPublicFromModulus() {
//        try {
//            BigInteger modulus = (
//                new BigInteger("00e79ea1f78b29f0c9b861b0939414915ba81ecd02ec88d83935312ba83dbffb5c41027b14e33bcf7988e1fc1c4d693e8ff3265c0a1a2bb874d42125e81f2654d36aee76def830b0936175391ec523a9329774fabf38356946b66b68e9f1362fb10ff2dc9c20dcdaf9ff809e289c03c6d80bff9b82aa606b92751ae8b2edfe99b1", 16)); // 1024
//            BigInteger exp = new BigInteger("010001", 16);
//
//            KeyFactory keyFactory = null;
//            keyFactory = KeyFactory.getInstance("RSA");
//            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exp);
//            return keyFactory.generatePublic(keySpec);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    private static PublicKey getPublicFromDer(Context mContext) throws Exception {
//        InputStream instream = mContext.getResources().openRawResource(R.raw.public_key); //todo change this
//        byte[] encodedKey = new byte[instream.available()];
//        instream.read(encodedKey);
//
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedKey);
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        return kf.generatePublic(spec);
//    }
//
//    private static PublicKey getPublicFromPem(Context context) {
//        try {
//            byte[] publicBytes = Base64.decode(getStringPublicKey(context), Base64.NO_WRAP);
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return keyFactory.generatePublic(keySpec);
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    private static String getStringPublicKey(Context mContext) {
//        String headerPublicKey = "-----BEGIN PUBLIC KEY-----";
//        String footerPublicKey = "-----END PUBLIC KEY-----";
//        InputStream input = mContext.getResources().openRawResource(R.raw.jm_pub); //todo change this
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        byte buf[] = new byte[1024];
//        int len;
//        try {
//            while ((len = input.read(buf)) != -1) {
//                outputStream.write(buf, 0, len);
//            }
//            outputStream.close();
//            input.close();
//        } catch (IOException e) {
//            System.out.println(e.toString());
//        }
//        return outputStream.toString().replace(headerPublicKey, "").replace(footerPublicKey, "");
//    }

    public static String encryptMd5(String plain) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(plain.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xff & aMessageDigest);
                if (hex.length() == 1) hexString.append("0");
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase(Locale.getDefault());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static SSLSocketFactory certificateVerificator(Context context) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        // Generate the certificate using the certificate file under res/raw/cert.cer
        InputStream caInput = new BufferedInputStream(context.getResources().openRawResource(R.raw.kos_ssl));//todo change this
        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore trusted = KeyStore.getInstance(keyStoreType);
        trusted.load(null, null);
        trusted.setCertificateEntry("ca", ca);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(trusted);

        // Create an SSLContext that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext.getSocketFactory();
    }
}
