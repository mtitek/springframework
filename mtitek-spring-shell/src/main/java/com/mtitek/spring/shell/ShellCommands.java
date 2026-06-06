package com.mtitek.spring.shell;

import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

@Component
public class ShellCommands {

	@Command(name = "min-value", description = "Display Min Value")
	public int minValue() {
		return 1_000;
	}

	@Command(name = "max-value", description = "Display Max Value")
	public void maxValue() {
		System.out.println(1_000_000);
	}

	@Command(name = "to-lower-case", description = "Convert to lower case")
	public String toLowerCase(
			@Option(longName = "input", shortName = 'i', description = "String to convert", defaultValue = "HELLO") String input) {
		return input.toLowerCase();
	}

	@Command(name = "add-values", description = "Add two values")
	public Integer addValues(@Option(shortName = 'a') Integer a, @Option(shortName = 'b') Integer b) {
		return a + b;
	}
}
