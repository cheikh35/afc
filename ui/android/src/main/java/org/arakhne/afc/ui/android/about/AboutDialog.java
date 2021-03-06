/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2016 The original authors, and other authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.arakhne.afc.ui.android.about;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.arakhne.afc.ui.android.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;

/** Dialog that is displaying a "about this app" message.
 * 
 * @author $Author: sgalland$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @deprecated see JavaFX API
 */
@Deprecated
public class AboutDialog extends Dialog {

	private final int applicationIconId;
	private final String legalAssetName;
	private final String infoAssetName;
	
	/** Create the "About" dialog with the default configuration.
	 * The description of the application is read for the asset file
	 * {@code "info.html"} (HTML format).
	 * The legal text is read from the asset file {@code "legal.html"}
	 * (HTML format).
	 * 
	 * @param context
	 * @see #AboutDialog(Context, int, String, String)
	 */
	public AboutDialog(Context context) {
		this(context, 0, null, null);
	}

	/** Create the "About" dialog with the default configuration.
	 * 
	 * @param context
	 * @param applicationIcon is the identifier of the application icon.
	 * @param infoAsset is the name of the asset file that is a HTML file with the description of the application.
	 * By default it is {@code "info.html"}.
	 * @param legalAsset is the name of the asset file that is a HTML file with the legal text to display in the dialog box.
	 * By default it is {@code "legal.html"}.
	 */
	public AboutDialog(Context context, int applicationIcon, String infoAsset, String legalAsset) {
		super(context);
		this.applicationIconId = applicationIcon;
		if (infoAsset==null || infoAsset.isEmpty())
			this.infoAssetName = "info.html"; 
		else
			this.infoAssetName = infoAsset;
		if (legalAsset==null || legalAsset.isEmpty())
			this.legalAssetName = "legal.html"; 
		else
			this.legalAssetName = legalAsset;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTitle(R.string.about_this_app);
		setContentView(R.layout.about);
		if (this.applicationIconId>0) {
			ImageView iv = (ImageView)findViewById(R.id.about_application_id);
			iv.setImageResource(this.applicationIconId);
		}
		TextView tv = (TextView)findViewById(R.id.about_legal_text);
		tv.setText(Html.fromHtml(readAsset(this.legalAssetName)));
		tv = (TextView)findViewById(R.id.about_info_text);
		tv.setText(Html.fromHtml(readAsset(this.infoAssetName)));
		Linkify.addLinks(tv, Linkify.EMAIL_ADDRESSES | Linkify.WEB_URLS);
	}

	private String readAsset(String assetName) {
		try {
			InputStream inputStream = getContext().getAssets().open(assetName);
			try {
				InputStreamReader in = new InputStreamReader(inputStream);
				BufferedReader buf = new BufferedReader(in);
				String line;
				StringBuilder text = new StringBuilder();
				while (( line = buf.readLine()) != null) {
					text.append(line);
				}
				return text.toString();
			}
			finally {
				inputStream.close();
			}
		}
		catch (IOException exception) {
			return null;
		}
	}
}