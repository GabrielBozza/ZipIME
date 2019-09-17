package cifracesar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 * @author g-boz
 */
//CODIFICACESAR-->CODIFICAHUFFMAN--->DECODIFICAHUFFMAN--->DECODIFICACESAR
class HuffmanNode {

    int data;//FREQUENCIA DO CARACTERE
    char c;

    HuffmanNode left;
    HuffmanNode right;
}

class MyComparator implements Comparator<HuffmanNode> {//COMPARA AS FREQUENCIAS 

    public int compare(HuffmanNode x, HuffmanNode y) {

        return x.data - y.data;
    }
}

public class CifraCesar {

    public String CodificaCesar(String s, int n) {

        char[] Cod = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {

            if (Cod[i] < 91 && Cod[i] > 64 && (char) (Cod[i] + n) >= 91) {

                Cod[i] = (char) (((Cod[i] + n) % 91) + 65);

            } else if (Cod[i] < 91 && Cod[i] > 64 && (char) (Cod[i] + n) < 91) {

                Cod[i] = (char) ((Cod[i] + n) % 91);

            } else if (Cod[i] < 123 && Cod[i] > 96 && (char) (Cod[i] + n) < 123) {

                Cod[i] = (char) ((Cod[i] + n) % 123);

            } else if (Cod[i] < 123 && Cod[i] > 96 && (char) (Cod[i] + n) >= 123) {

                Cod[i] = (char) (((Cod[i] + n) % 123) + 97);
            }

        }

        String p = new String(Cod);
        return p;
    }

    public String DecodificaCesar(String s, int n) {

        n = 26 - n;

        char[] Cod = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {

            if (Cod[i] < 91 && Cod[i] > 64 && (char) (Cod[i] + n) >= 91) {

                Cod[i] = (char) (((Cod[i] + n) % 91) + 65);

            } else if (Cod[i] < 91 && Cod[i] > 64 && (char) (Cod[i] + n) < 91) {

                Cod[i] = (char) ((Cod[i] + n) % 91);

            } else if (Cod[i] < 123 && Cod[i] > 96 && (char) (Cod[i] + n) < 123) {

                Cod[i] = (char) ((Cod[i] + n) % 123);

            } else if (Cod[i] < 123 && Cod[i] > 96 && (char) (Cod[i] + n) >= 123) {

                Cod[i] = (char) (((Cod[i] + n) % 123) + 97);
            }

        }

        String p = new String(Cod);
        return p;

    }

    public int[] CharFreqs(String s) {

        int[] freqs;//ARRAY DE FREQS DE {A,B,C,...,Z,a,b,c,...,z,' '}
        freqs = new int[58];

        char[] p = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {

            if (p[i] < 91 && p[i] > 64) {//LETRAS MAIUSCULAS
                freqs[p[i] - 65]++;
            } else if (p[i] > 96 && p[i] < 123) {//LETRAS MINUSCULAS
                freqs[p[i] - 71]++;
            } else if (p[i] == 32) {
                freqs[52]++;//CONTA O NUMERO DE ESPACOS EM BRANCO!!!
            } else if (p[i] == 44) {
                freqs[53]++;
            } else if (p[i] == 46) {
                freqs[54]++;
            } else if (p[i] == 33) {
                freqs[55]++;
            } else if (p[i] == 63) {
                freqs[56]++;
            } else if (p[i] == 59) {
                freqs[57]++;
            }

        }

        return freqs;
    }

    public Map<String, Character> Huffman(HuffmanNode root, String s, Map<String, Character> a) {//RETORNA UM VECTOR DE STRINGS COM OS CODIGOS DE HUFFMAN DE ACORDO COM A PRIORITYQUEUE

        if (root.left == null && root.right == null && Character.isLetter(root.c) || root.c == ' ' || root.c == ',' || root.c == '.' || root.c == '!' || root.c == '?' || root.c == ';') {
            a.put(s, root.c);
            return a;
        } else {
            Huffman(root.left, s + "0", a);
            Huffman(root.right, s + "1", a);
            return a;
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        Scanner s = new Scanner(System.in);
        BufferedReader objReader = null;
        CifraCesar Cifra = new CifraCesar();
        int ChaveCesar = 12;

        String strCurrentLine;
        String Texto = "";

        objReader = new BufferedReader(new FileReader("TextoASerCodificado.txt"));

        while ((strCurrentLine = objReader.readLine()) != null) {

            Texto = Texto.concat(" ");//ADICIONA UM ESPACO ENTRE CADA LINHA
            Texto = Texto.concat(strCurrentLine);//TRANSFORMA O TEXTO TODO EM UMA UNICA STRING
        }

        Texto = Cifra.CodificaCesar(Texto, ChaveCesar);
        int[] freqs = Cifra.CharFreqs(Texto);//CRIA O ARRAY DAS FREQUENCIAS DOS CARACTERES

        BufferedWriter writer = new BufferedWriter(new FileWriter("TextoCodifCesar.txt"));//APAGA O QUE JAH TEM NO ARQ E ESCREVE-->CRIA O ARQ CASO ESSE NAO EXISTA!
        writer.write(Texto);
        writer.close();

        Map<String, Character> Huff = new HashMap<String, Character>(58);

        // NUMERO DE CARACTERES 
        int n = 58;
        char[] charArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ', ',', '.', '!', '?', ';'};
        int[] charfreq = Cifra.CharFreqs(Texto);//JAH CODIFICADO PELA CIFRA DE CESAR

        PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(n, new MyComparator());

        for (int i = 0; i < n; i++) {

            HuffmanNode hn = new HuffmanNode();

            hn.c = charArray[i];
            hn.data = charfreq[i];
            hn.left = null;
            hn.right = null;
            q.add(hn);
        }

        HuffmanNode root = null;

        while (q.size() > 1) {

            // first min extract. 
            HuffmanNode x = q.peek();
            q.poll();

            // second min extarct. 
            HuffmanNode y = q.peek();
            q.poll();

            // new node f which is equal 
            HuffmanNode f = new HuffmanNode();

            f.data = x.data + y.data;
            f.c = '-';
            f.left = x;
            f.right = y;
            root = f;
            q.add(f);
        }

        String f = "";
        char[] l = Texto.toCharArray();
        Cifra.Huffman(root, "", Huff);
        for (int i = 0; i < Texto.length(); i++) {

            for (Map.Entry<String, Character> entry : Huff.entrySet()) {

                if (l[i] == entry.getValue()) {
                    f = f + entry.getKey();
                }

            }
        }
        BufferedWriter writer2 = new BufferedWriter(new FileWriter("TextoCodifCesar&Huffman.txt"));//APAGA O QUE JAH TEM NO ARQ E ESCREVE-->CRIA O ARQ CASO ESSE NAO EXISTA!
        writer2.write(f);
        writer2.close();

        //System.out.println(Arrays.toString(b)); // easier print once finished

        //DECODIIFICA HUFFMAN
        char[] w = f.toCharArray();
        String g = "";
        String h = "";

        for (int i = 0; i < f.length(); i++) {
            g = g + w[i];
            for (Map.Entry<String, Character> entry : Huff.entrySet()) {
                if (g.equals(entry.getKey())) {
                    h = h + entry.getValue();
                    g = "";
                }
            }
        }

        BufferedWriter writer3 = new BufferedWriter(new FileWriter("TextoDecodifHuffman.txt"));//APAGA O QUE JAH TEM NO ARQ E ESCREVE-->CRIA O ARQ CASO ESSE NAO EXISTA!
        writer3.write(h);
        writer3.close();

        //DECODIFICA CESAR
        BufferedWriter writer4 = new BufferedWriter(new FileWriter("TextoDecodifHuffman&Cesar.txt"));//APAGA O QUE JAH TEM NO ARQ E ESCREVE-->CRIA O ARQ CASO ESSE NAO EXISTA!
        writer4.write(Cifra.DecodificaCesar(h, ChaveCesar));
        writer4.close();
    }
}
