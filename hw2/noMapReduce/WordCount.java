import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class WordCount {
	public Map<String, Integer> countMap;

	public WordCount() {
		countMap = new HashMap<>();
	}

	public void createMap(String[] words) {
		for (String s : words) {
			countMap.put(s.toLowerCase(), 0);
		}
	}

	public void checkALine(String line) {
		String delims = "[ ,;#-/:]+";
		String[] tokens = line.split(delims);
		Set<String> set = new HashSet<>(Arrays.asList(tokens));

		for (String s : set) {
			String s1 = s.toLowerCase();
			if (countMap.containsKey(s1)) {
				countMap.put(s1, countMap.get(s1) + 1);
			}
		}
	}


	public static final void main(String[] args) throws IOException {
		WordCount wc = new WordCount();
		String[] words = args[0].split(", ");
		wc.createMap(words);

		File inputFile = new File(args[1]);
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		String line;
		while ((line = br.readLine()) != null) {
			wc.checkALine(line);
		}

		String outputFile = "output.txt";
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		for (String word : words) {
			bw.write(word + " " + wc.countMap.get(word.toLowerCase()));
			bw.newLine();
			System.out.println(word + " " + wc.countMap.get(word.toLowerCase()));
		}

		br.close();
		bw.close();
	}
}