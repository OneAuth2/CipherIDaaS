package cipher.jms.consumer.util.ronglian.encoder;

import java.io.*;
import java.nio.ByteBuffer;

public abstract class CharacterDecoder {
    /** Return the number of bytes per atom of decoding */
    abstract protected int bytesPerAtom();

    /** Return the maximum number of bytes that can be encoded per line */
    abstract protected int bytesPerLine();

    /** decode the beginning of the buffer, by default this is a NOP. */
    protected void decodeBufferPrefix(PushbackInputStream aStream,
                                      OutputStream bStream) throws IOException
    {
    }

    /** decode the buffer suffix, again by default it is a NOP. */
    protected void decodeBufferSuffix(PushbackInputStream aStream,
                                      OutputStream bStream) throws IOException
    {
    }

    /**
     * 103 * This method should return, if it knows, the number of bytes 104 *
     * that will be decoded. Many formats such as uuencoding provide 105 * this
     * information. By default we return the maximum bytes that 106 * could have
     * been encoded on the line. 107
     */
    protected int decodeLinePrefix(PushbackInputStream aStream,
                                   OutputStream bStream) throws IOException
    {
        return (bytesPerLine());
    }

    /**
     * 113 * This method post processes the line, if there are error detection
     * 114 * or correction codes in a line, they are generally processed by 115
     * * this method. The simplest version of this method looks for the 116 *
     * (newline) character. 117
     */
    protected void decodeLineSuffix(PushbackInputStream aStream,
                                    OutputStream bStream) throws IOException
    {
    }

    /**
     * 121 * This method does an actual decode. It takes the decoded bytes and
     * 122 * writes them to the OutputStream. The integer <i>l</i> tells the 123
     * * method how many bytes are required. This is always <= bytesPerAtom().
     * 124
     */
    protected void decodeAtom(PushbackInputStream aStream,
                              OutputStream bStream, int l) throws IOException
    {
        throw new CEStreamExhausted();
    }

    /**
     * 130 * This method works around the bizarre semantics of
     * BufferedInputStream's 131 * read method. 132
     */
    protected int readFully(InputStream in, byte buffer[], int offset, int len)
            throws IOException
    {
        for (int i = 0; i < len; i++)
        {
            int q = in.read();
            if (q == -1)
                return ((i == 0) ? -1 : i);
            buffer[i + offset] = (byte) q;
        }
        return len;
    }

    /**
     * 145 * Decode the text from the InputStream and write the decoded 146 *
     * octets to the OutputStream. This method runs until the stream 147 * is
     * exhausted. 148 * @exception CEFormatException An error has occured while
     * decoding 149 * @exception CEStreamExhausted The input stream is
     * unexpectedly out of data 150
     */
    public void decodeBuffer(InputStream aStream, OutputStream bStream)
            throws IOException
    {
        int i;
        int totalBytes = 0;

        PushbackInputStream ps = new PushbackInputStream(aStream);
        decodeBufferPrefix(ps, bStream);
        while (true)
        {
            int length;

            try
            {
                length = decodeLinePrefix(ps, bStream);
                for (i = 0; (i + bytesPerAtom()) < length; i += bytesPerAtom())
                {
                    decodeAtom(ps, bStream, bytesPerAtom());
                    totalBytes += bytesPerAtom();
                }
                if ((i + bytesPerAtom()) == length)
                {
                    decodeAtom(ps, bStream, bytesPerAtom());
                    totalBytes += bytesPerAtom();
                } else
                {
                    decodeAtom(ps, bStream, length - i);
                    totalBytes += (length - i);
                }
                decodeLineSuffix(ps, bStream);
            } catch (CEStreamExhausted e)
            {
                break;
            }
        }
        decodeBufferSuffix(ps, bStream);
    }

    /**
     * 182 * Alternate decode interface that takes a String containing the
     * encoded 183 * buffer and returns a byte array containing the data. 184 * @exception
     * CEFormatException An error has occured while decoding 185
     */
    public byte decodeBuffer(String inputString)[] throws IOException
    {
        byte inputBuffer[] = new byte[inputString.length()];
        ByteArrayInputStream inStream;
        ByteArrayOutputStream outStream;

        inputString.getBytes(0, inputString.length(), inputBuffer, 0);
        inStream = new ByteArrayInputStream(inputBuffer);
        outStream = new ByteArrayOutputStream();
        decodeBuffer(inStream, outStream);
        return (outStream.toByteArray());
    }

    /**
     * 199 * Decode the contents of the inputstream into a buffer. 200
     */
    public byte decodeBuffer(InputStream in)[] throws IOException
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        decodeBuffer(in, outStream);
        return (outStream.toByteArray());
    }

    /**
     * 208 * Decode the contents of the String into a ByteBuffer. 209
     */
    public ByteBuffer decodeBufferToByteBuffer(String inputString)
            throws IOException
    {
        return ByteBuffer.wrap(decodeBuffer(inputString));
    }

    /**
     * 216 * Decode the contents of the inputStream into a ByteBuffer. 217
     */
    public ByteBuffer decodeBufferToByteBuffer(InputStream in)
            throws IOException
    {
        return ByteBuffer.wrap(decodeBuffer(in));
    }
}
