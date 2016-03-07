package utils;
 

 
 /** 
 * Classe utilisee pour convertir des  int ou des short en tableaux de bytes et vice-versa
 */
 public final class Converter{
  
  private Converter() {}			  // no public constructor
 
 /**
* Converts an integer into a 4-byte array
*@param i integer to convert
*@return byte[]  4-byte array representing the integer
*/
 public static byte[] IntToByteArr(int i) throws ArrayIndexOutOfBoundsException
 {
         
         byte[] b = new byte[4];
         b[0] = (byte)( (0xff000000 & i) >>> 24);
        b[1] = (byte)( (0x00ff0000 & i) >>> 16);
        b[2] = (byte)( (0x0000ff00 & i) >>>  8);
        b[3] = (byte)( (0x000000ff & i) >>>  0);
         
         return b;
         
 } // IntToByteArr()

  
/**
* Converts a short into a 2-byte array
*@param i integer to convert
*@return byte[]  2-byte array representing the integer
*/
 public static byte[] ShortToByteArr(short i) throws ArrayIndexOutOfBoundsException
 {
         
  
           byte[] array = new byte[2];
      array[0] = (byte)(( 0x0000ff00 & i) >>> 8);
      array[1] = (byte)((0x000000ff & i) >>> 0);
      return array;
       
         
 } // ShortToByteArr()
  
/**
* Converts a 4-byte array into an integer
*@param bA 4-byte array
*@return int conversion of the 4-byte array
*/
 public static int ByteArrToInt(byte[] bA)
   throws ArrayIndexOutOfBoundsException
 {
  if (bA.length != 4)
   throw new ArrayIndexOutOfBoundsException("Array must be 4 bytes");

  int result = ((int)bA[0] << 24) & 0xff000000;
  result |= ((int)bA[1] << 16) & 0x00ff0000;
  result |= ((int)bA[2] << 8) & 0x0000ff00;
  result |= ((int)bA[3]) & 0x000000ff;

  return result;
 } // ByteArrToInt()






/**
* Converts a 2-byte array into a short
*@param bA 2-byte array
*@return short conversion of the 2-byte array
*/
 public static short ByteArrToShort(byte[] bA)
   throws ArrayIndexOutOfBoundsException
 {
  if (bA.length != 2)
   throw new ArrayIndexOutOfBoundsException("Array must be 2 bytes");



 int result =  ((short)bA[0] << 8) & 0x0000ff00;
  result |= ((short)bA[1]) & 0x000000ff;

  return (short) result;



 } // ByteArrToShort()
  
  
  
  } //Converter.java
