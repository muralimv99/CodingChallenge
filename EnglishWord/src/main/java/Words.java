import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Words {

    public static void main( String[] args ) throws IOException
    {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL is = classloader.getResource("data/words.txt");

        List<String> list = Files.readAllLines( new File( is.getPath()).toPath(), Charset.defaultCharset() );

        List<String> lowercase = list.stream().map(String::toLowerCase).filter(s->s.chars().allMatch(Character::isLetter)).collect( Collectors.toList());

        System.out.println( "Read " + lowercase.size() + " words" );

        String str = "zwa";
        for (int i=0; i < str.length(); i++)
        {
            permutation( "", str.substring(i,str.length()));
        }

    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) System.out.println(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
    }

    private static void findOptions( String string, List<String> lowercase )
    {
        int[] freq = toFreq( string );
        for ( String l : lowercase )
        {
            int[] freqIn = toFreq( l );
            if ( matches( freq, freqIn ) )
            {
                System.out.println( l );
            }
        }
    }

    /**
     * Returns true if all the frequencies of the letters match.
     *
     * @param freq
     * @param freqIn
     * @return
     */
    private static boolean matches( int[] freq, int[] freqIn )
    {
        for ( int i = 0; i < 26; i++ )
        {
            if ( freq[i] == 0 && freqIn[i]>0)
            {
                return false;
            }
            else if (freq[i] < freqIn[i])
            {
                return false;
            }

        }
        return true;
    }

    /**
     * Encode a word in to a frequency array. int[0] = #a's, int[1] = #b's etc.
     *
     * @param string
     * @return
     */
    private static int[] toFreq( String string )
    {
        int[] freq = new int[26];
        for ( char c : string.toCharArray() )
        {
            if ( ( c - 'a' ) >= 0 && ( c - 'a' ) < 26 )
            {
                freq[c - 'a']++;
            }
        }
        return freq;
    }

}
