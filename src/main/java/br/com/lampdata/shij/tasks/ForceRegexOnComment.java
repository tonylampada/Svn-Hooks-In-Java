package br.com.lampdata.shij.tasks;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.lampdata.shij.SVNDelegate;
import br.com.lampdata.shij.Task;
import br.com.lampdata.shij.TaskException;

public class ForceRegexOnComment extends Task {

	@Override
	public void execute(String transaction, String repository) throws TaskException {
		SVNDelegate svn = new SVNDelegate(transaction, repository);
		String comment = svn.getLog();
		String author = svn.getAuthor();

		String regex = props.getProperty("regex");
		String message = props.getProperty("message");

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(comment);
		if(!m.matches()){
			String err = MessageFormat.format(message, author, comment);
			throw new TaskException(err);
		}
	}

	public static void main(String[] args) {
//		JIRA = [a-zA-Z]+\\-\\d+(\\s+.*)*
		Pattern p = Pattern.compile("[a-zA-Z]+\\-\\d+(\\s+.*)*");
		Matcher m = p.matcher("BUG-123");
		System.out.println(m.matches());
	}

}
