import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class HuffmanCompression {
	public static void main(String[] args) {
		/*--Huffman Tree Construction.--*/	
		int[] frequency = new int[255];
		String s = "Llueve en este poema\r\n" + 
				   "Eduardo Carranza.\r\n" + 
					"\r\n" + 
					"Llueve. La tarde es una\r\n" + 
					"hoja de niebla. Llueve.\r\n" + 
					"La tarde está mojada\r\n" + 
					"de tu misma tristeza. Ian me la pela['[[[";
		int i;
		//Obtain characters frequency.
		for(i = 0; i < s.length(); i++)frequency[(int)s.charAt(i)]++;	
		PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
		HuffmanNode hn;	
		//Filling PriorityQueue with HuffmanNodes.
		for(i = 0; i < frequency.length; i++) {
			if(frequency[i] > 0) {
				hn = new HuffmanNode(frequency[i], (char)i);
				queue.add(hn);
			}
		}
		//Extraction of HuffmanNodes to create the tree.
		while(queue.size() > 1) {
			HuffmanNode p = queue.poll();
			HuffmanNode q = queue.poll();
			hn = new HuffmanNode((p.weight + q.weight), '\0');
			hn.left = p;
			hn.right  = q;
			queue.add(hn);		
		}
		//Get the Huffman Tree root
		hn = queue.poll();		
		/*--Symbol encoding--*/
		TreeMap<Character, String> encoding = new TreeMap<>();
		huffmanEncoding(encoding, hn,"");
		/*--Text encoding--*/
		String encodedS = "";
		for(i = 0; i < s.length(); i++)encodedS += encoding.get(s.charAt(i));
	
		String aux;
		ArrayDeque<String> byt = new ArrayDeque<>();
		int count = 0, bytee = 8;
		while(bytee <= encodedS.length()) {
			aux = encodedS.substring(count, bytee);
			count = bytee;
			bytee += 8;
			byt.add(aux);
		}
		if(bytee > encodedS.length()) {
			aux = encodedS.substring(count, encodedS.length());
			for(i = 0; i < 8 - (encodedS.length() - count); i++)aux += "0";
			byt.add(aux);
		}
				
		byte[] bytesArray = new byte[byt.size()];
		for(i = 0; i < bytesArray.length; i++) {
			String num = byt.poll();
			int a = Integer.parseInt(num,2);
			bytesArray[i] = (byte) a;
		}
				
		/*Binary File*/
		String filename = "file.bin";
		FileOutputStream fileOs;
		try {
			fileOs = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(fileOs);
			for(i = 0; i < 255; i++)os.writeInt(frequency[i]);
			os.writeInt(bytesArray.length);
			os.write(bytesArray);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileInputStream fileIs = new FileInputStream(filename);
			ObjectInputStream is = new ObjectInputStream(fileIs);
			int readI;
			byte read;
			int nChars = 0;	
			int f;
			int []fre = new int[255];
			for(i = 0; i < 255; i++) {
				f = is.readInt();
				fre[i] = f;
			}
			HuffmanNode root = buildTree(fre);
			readI = is.readInt();
			HuffmanNode n = root;
			for(i = 0; i < readI; i++) {
				read = is.readByte();
				int nBit = 1;
				byte au = read;
				while( nBit <= 8) {
					au >>>= (8 - nBit);
					au &= 1;
					if(au == 0) n = n.left;
					else if(au == 1) n = n.right;
					if(n.left == null && n.right == null) {
						System.out.print(n.symbol);
						n = root;
						nChars++;
					}		
					if(nChars == root.weight)break;

					nBit++;
					au = read;
				}
			}
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public static class HuffmanNode implements Comparable<Object>{
		int weight;
		char symbol;
		HuffmanNode left = null;
		HuffmanNode right = null;
		
		public HuffmanNode(int weight, char symbol) {
			this.weight = weight;
			this.symbol = symbol;
		}
		
		@Override
		public int compareTo(Object arg0) {
			if(arg0 instanceof HuffmanNode) {
				HuffmanNode aux = (HuffmanNode)arg0;
				return this.weight - aux.weight;
			}
			return 0;
		}
		public String toString() {
			return symbol + " " + weight;
		}	
	}
	
	public static void huffmanEncoding(TreeMap<Character, String> encoding, HuffmanNode root, String s) {
        if (root.left == null && root.right == null) {
            encoding.put(root.symbol, s);
            return ;
        }
        huffmanEncoding(encoding, root.left, s + "0");
        huffmanEncoding(encoding, root.right, s + "1");
	}

	private static void print(HuffmanNode node, int spaces) {
		if (node != null) {
			String str = "";
			for(int i = 0; i < spaces; i ++) str += " ";
			System.out.println(str + node);
			print(node.left, spaces + 2);
			print(node.right, spaces + 2);
		}
	}

	public static void print(HuffmanNode root) {
		print(root, 0);
		System.out.println("-------");
	}
	
	public static void printBinValue(Byte b) {
		int nBit = 1;
		byte aux = b;
		String bin  = "";
		while( nBit <= 8) {
			aux >>>= (8 - nBit);
			aux &= 1;
			bin += (""+ aux);			
			nBit++;
			aux = b;
		}
		System.out.println(bin);
	}

	public static HuffmanNode buildTree(int[] frequency) {
		PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
		HuffmanNode hn;	
		//Filling PriorityQueue with HuffmanNodes.
				for(int i = 0; i < frequency.length; i++) {
					if(frequency[i] > 0) {
						hn = new HuffmanNode(frequency[i], (char)i);
						queue.add(hn);
					}
				}
				//Extraction of HuffmanNodes to create the tree.
				while(queue.size() > 1) {
					HuffmanNode p = queue.poll();
					HuffmanNode q = queue.poll();
					hn = new HuffmanNode((p.weight + q.weight), '\0');
					hn.left = p;
					hn.right  = q;
					queue.add(hn);		
				}
			return queue.poll();
	}
}
