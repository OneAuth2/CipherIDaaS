// import encrypt from '@/util/rsa.js'

 // Encrypt with the public key...
 var encrypt = new JSEncrypt();
 encrypt.setPublicKey(encrypt.publicKey);
 var encrypted = encrypt.encrypt(data);
 