package it.xpug.frameworkless.hangman.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordList {
	private List<String> words = new ArrayList<String>() {{
		add("advise");
		add("affinely");
		add("american");
		add("applauded");
		add("assurance");
		add("beggar");
		add("biddable");
		add("brigade");
		add("chemistry");
		add("cherish");
		add("classical");
		add("coma");
		add("creeds");
		add("decadence");
		add("deliberation");
		add("discount");
		add("disorderly");
		add("embassy");
		add("enthusiasm");
		add("footrest");
		add("giant");
		add("grandson");
		add("grasp");
		add("green");
		add("guard");
		add("handhold");
		add("idiotic");
		add("ineligibility");
		add("inquire");
		add("inquisitive");
		add("learned");
		add("legibility");
		add("mesmerize");
		add("misshapen");
		add("misstep");
		add("mobile");
		add("moonsick");
		add("necessary");
		add("nightcap");
		add("obligation");
		add("octaedron");
		add("peroxidation");
		add("phototherapy");
		add("physician");
		add("precaution");
		add("problematic");
		add("quicken");
		add("repay");
		add("repurpose");
		add("rubbish");
		add("scabrous");
		add("scanty");
		add("scarceness");
		add("seismic");
		add("shortsighted");
		add("snowlike");
		add("succeed");
		add("sunny");
		add("superman");
		add("tautonym");
		add("thimble");
		add("thinkling");
		add("underrate");
		add("uniform");
		add("unstacker");
		add("waterline");
		add("whence");
	}};
	private Random random;

	public WordList() {
		this(new Random());
	}

	public WordList(Random random) {
		this.random = random;
	}

	public String getRandomWord() {
		return words.get(random.nextInt(words.size()));
	}
}
