package it.xpug.frameworkless.hangman.domain;

import java.util.*;

public class WordList {
	private List<String> words = new ArrayList<String>() {{
		add("repay");
		add("waterline");
		add("guardhouse");
		add("uniform");
		add("footrest");
		add("misstep");
		add("problematic");
		add("nightcap");
		add("whence");
		add("brigade");
		add("decadence");
		add("disorderly");
		add("underrate");
		add("rubbish");
		add("quicken");
		add("legibility");
		add("assurance");
		add("sunny");
		add("thimble");
		add("advise");
		add("american");
		add("beggar");
		add("embassy");
		add("grasp");
		add("scanty");
		add("ineligibility");

		add("biddable");
		add("grandsonship");
		add("aucupate");
		add("octaemeron");
		add("unartistical");
		add("affinely");
		add("thinkling");
		add("phototherapy");
		add("myiodesopsia");
		add("chemiatric");
		add("preappoint");
		add("scabrously");
		add("mesmerist");
		add("succeed");
		add("tautonym");
		add("peroxidation");
		add("unstacker");
		add("inquire");
		add("classical");
		add("moonsick");
		add("cingulate");
		add("misshapen");
		add("scarceness");
		add("physician");
		add("discount");
		add("repurpose");
		add("superinquisitive");
		add("nonnecessary");
		add("mobile");
		add("green");
		add("handhold");
		add("cherish");
		add("enthusiastical");
		add("deliberation");
		add("snowlike");
		add("learned");
		add("burnbeat");
		add("coma");
		add("idiotic");
		add("creeds");
		add("applauded");
		add("superman");
		add("exogamy");
		add("parotiditis");
		add("larky");
		add("astigmia");
		add("obligation");
		add("giant");
		add("infratrochlear");
		add("precaution");
		add("seismic");
		add("shortsighted");
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
