package jp.gr.java_conf.a_kura.ant.runfscscanner;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.EnumeratedAttribute;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class RunFscScannerTask extends Task {
	/** スキャナーURL */
	private static final String SCANNER_URL = "http://security.force.com/security/tools/forcecom/scanner";
	/** 待ち時間 */
	private static final int WAIT_TIME = 30;

	/**
	 * RunFscScannerタスク
	 */
	@Override
	public void execute() throws BuildException {
		validate();
		runFscScanner();
	}

	/**
	 * 引数のバリデーション
	 * @throws BuildException
	 */
	protected void validate() throws BuildException {
		if(username == null) {
			throw new BuildException("Username attribute must be set!");
		}
		if(scanProfile == null) {
			throw new BuildException("select attribute must be set!");
		}
	}

	/**
	 * Force.com Security Source Code Scannerを実行する
	 */
	protected void runFscScanner() {
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(WAIT_TIME, TimeUnit.SECONDS);

		driver.get(SCANNER_URL);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(username);
        driver.findElement(By.id("id_description")).clear();
		driver.findElement(By.id("id_description")).sendKeys(description);
        new Select(driver.findElement(By.id("id_scantype")))
				.selectByVisibleText(scanProfile.getValue());
        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
		driver.findElement(By.linkText("Accept")).click();
		
		System.out.println("Run Force.com Security Source Code Scanner!");

		driver.quit();
	}

	/** ScanProfile一覧 */
	protected static final String[] SCAN_PROFILE_LIST = { "All Rules", "Security Rules", "Quality Rules", "Beta Rules (+CRUD/FLS)", "Quality Beta Rules" };
	/** スキャン対象組織のユーザ名 */
	protected String username;
	/** スキャン時の説明 */
	protected String description;
	/** スキャン時のScanProfile */
	protected SelectScanProfile scanProfile;

	/**
	 * スキャン対象組織のユーザ名をセットする
	 * @param un ユーザ名
	 */
	public void setUsername(String un) {
		username = un;
	}
	
	/**
	 * スキャン時の説明をセットする
	 * @param d 説明
	 */
	public void setDescription(String d) {
		description = d;
	}

	/**
	 * ScanProfileの属性クラス
	 */
	public static class SelectScanProfile extends EnumeratedAttribute {
		@Override
		public String[] getValues() {
			// 属性値の一覧を返す
			return SCAN_PROFILE_LIST;
		}
	}

	/**
	 * ScanProfileをセットする
	 * @param s ScanProfile
	 */
	public void setScanProfile(SelectScanProfile s) {
		scanProfile = s;
	}
	
	/**
	 * コマンドラインで実行する
	 * @param args 引数()
	 */
    public static void main(String[] args) {
    	// バリデーション
    	if(args.length<3) {
    		System.out.println("Usage: java RunFscScannerTask Username Description ScanProfile");
    		return;
    	}
    	if(!Arrays.asList(SCAN_PROFILE_LIST).contains(args[2])) {
    		System.out.print("Usage: ScanProfile=");
    		for(int i=0; i<SCAN_PROFILE_LIST.length; i++) {
    			if(i>0) {
    				System.out.print("/");
    			}
    			System.out.print(SCAN_PROFILE_LIST[i]);
    		}
    		System.out.println("");
    		return;
    	}
    	
    	// 実行
    	RunFscScannerTask runner = new RunFscScannerTask();
    	
    	runner.setUsername(args[0]);
    	runner.setDescription(args[1]);
    	SelectScanProfile s = new SelectScanProfile();
    	s.setValue(args[2]);
    	runner.setScanProfile(s);
    	
		runner.execute();
	}
}
