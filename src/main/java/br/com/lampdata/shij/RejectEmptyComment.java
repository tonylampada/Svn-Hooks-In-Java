package br.com.lampdata.shij;

public class RejectEmptyComment extends Task {

	@Override
	public void execute(String transaction, String repository) throws TaskException {
		SVNDelegate svn = new SVNDelegate(transaction, repository);
		String comment = svn.getLog();
		if(comment.trim().length() == 0){
			String err = "Dear developer, "+svn.getAuthor()+":\n" +
				"This server was configured so that you *will* go through the trouble of typing a commit log, GOT IT?\n" +
				"Now be a good boy, and type a meaningful one.";
			throw new TaskException(err);
		}
	}

}
