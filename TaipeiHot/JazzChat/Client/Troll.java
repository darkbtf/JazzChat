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
			tempRespond.add("你在講三小啦");
			tempRespond.add("皇上駕崩~~~~~");
			tempRespond.add("吃飽了沒");
			tempRespond.add("你有聽過安麗嗎");
			tempRespond.add("你怎麼不去吃屎");
			tempRespond.add("你是誰");
			tempRespond.add("jizz");
			tempRespond.add("jizz");
			tempRespond.add("jizz");
			tempRespond.add("(1}");
			tempRespond.add("(2}");
			tempRespond.add("(3}");
			tempRespond.add("(4}");
			tempRespond.add("(5}");
			tempRespond.add("(6}");
			tempRespond.add("(7}");
			tempRespond.add("(8}");
			tempRespond.add("(10}");
			tempRespond.add("(11}");
		}
		int listSize = tempRespond.size();
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return tempRespond.get(random.nextInt(listSize));
	}
}
