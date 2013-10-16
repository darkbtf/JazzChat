package TaipeiHot.JazzChat.Client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Troll {

	public class Respond {
		String[] pattern;
		String[] respond;

		Respond(String[] pattern, String[] respond) {
			this.pattern = pattern;
			this.respond = respond;
		}
	}

	List<Respond> respondList = new ArrayList<Respond>();

	Troll(URL url) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(url.getPath()));
			System.out.println(url.getPath());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line1;
		String line2;
		try {
			while ((line1 = br.readLine()) != null) {
				line2 = br.readLine();
				System.out.println(line1);
				System.out.println(line2);
				respondList
						.add(new Respond(line1.split(" "), line2.split(" ")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String respondMessage(String message) {
		List<String> tempRespond = new ArrayList<String>();
		for (Respond respond : respondList) {
			boolean matched = true;
			for (String pat : respond.pattern) {
				if (!message.contains(pat)) {
					matched = false;
					break;
				}
			}
			if (!matched)
				continue;
			for (String res : respond.respond) {
				tempRespond.add(res);
			}
		}
		Random random = new Random();
		if (tempRespond.isEmpty()) {
			tempRespond.add("幹");
		}
		int listSize = tempRespond.size();
		return tempRespond.get(random.nextInt(listSize));
	}
}
