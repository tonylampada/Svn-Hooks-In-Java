package br.com.lampdata.shij;

import java.io.File;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.admin.SVNLookClient;

public class SVNDelegate {
	private String transaction, repository;
	private SVNLookClient svnLookClient;
	private File fRepository;

	public SVNDelegate(String transaction, String repository) {
		this.transaction = transaction;
		this.repository = repository;
		fRepository = new File(repository);
		svnLookClient = new SVNLookClient((ISVNAuthenticationManager)null, new DefaultSVNOptions());
	}
	
	public String getLog() {
		try {
			return svnLookClient.doGetLog(fRepository, transaction);
		} catch (SVNException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getAuthor() {
		try {
			return svnLookClient.doGetAuthor(fRepository, transaction);
		} catch (SVNException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
