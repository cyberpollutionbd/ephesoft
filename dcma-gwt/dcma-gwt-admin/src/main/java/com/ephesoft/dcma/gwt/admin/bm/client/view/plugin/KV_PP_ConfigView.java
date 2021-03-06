/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2012 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

package com.ephesoft.dcma.gwt.admin.bm.client.view.plugin;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.ephesoft.dcma.gwt.admin.bm.client.AdminConstants;
import com.ephesoft.dcma.gwt.admin.bm.client.presenter.plugin.KV_PP_ConfigPresenter;
import com.ephesoft.dcma.gwt.core.client.View;
import com.ephesoft.dcma.gwt.core.client.ui.table.Record;
import com.ephesoft.dcma.gwt.core.shared.KVPageProcessDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * This class provides functionality to edit KV PP configuration.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.gwt.core.client.View
 */
public class KV_PP_ConfigView extends View<KV_PP_ConfigPresenter> {

	/**
	 * UI binder.
	 */
	interface Binder extends UiBinder<DockLayoutPanel, KV_PP_ConfigView> {
	}

	/**
	 * kvConfigPanel DockLayoutPanel.
	 */
	@UiField
	protected DockLayoutPanel kvConfigPanel;

	/**
	 * addKVPPConfigButton Button.
	 */
	@UiField
	protected Button addKVPPConfigButton;

	/**
	 * editKVPPConfigButton Button.
	 */
	@UiField
	protected Button editKVPPConfigButton;

	/**
	 * deleteKVPPConfigButton Button.
	 */
	@UiField
	protected Button deleteKVPPConfigButton;

	/**
	 * kvPPConfigListView KV_PP_ConfigListView.
	 */
	private final KV_PP_ConfigListView kvPPConfigListView;

	/**
	 * Instantiates a class via deferred binding.
	 */

	private final Binder BINDER = GWT.create(Binder.class);

	/**
	 * Constructor.
	 */
	public KV_PP_ConfigView() {
		super();
		initWidget(BINDER.createAndBindUi(this));
		addKVPPConfigButton.setText(AdminConstants.ADD_BUTTON);
		editKVPPConfigButton.setText(AdminConstants.EDIT_BUTTON);
		deleteKVPPConfigButton.setText(AdminConstants.DELETE_BUTTON);
		kvPPConfigListView = new KV_PP_ConfigListView();
		kvConfigPanel.add(kvPPConfigListView.listView);
	}

	/**
	 * To get Kv Config Panel.
	 * 
	 * @return DockLayoutPanel
	 */
	public DockLayoutPanel getKvConfigPanel() {
		return kvConfigPanel;
	}

	/**
	 * To add Button Click Handler.
	 * 
	 * @param event ClickEvent
	 */
	@UiHandler("addKVPPConfigButton")
	public void addButtonClickHandler(ClickEvent event) {
		presenter.onAddKVPPClicked();
	}

	/**
	 * To edit Button Click Handler.
	 * 
	 * @param event ClickEvent
	 */
	@UiHandler("editKVPPConfigButton")
	public void editButtonClickHandler(ClickEvent event) {
		presenter.onEditKVPPClicked();
	}

	/**
	 * To delete Button Click Handler.
	 * 
	 * @param event ClickEvent
	 */
	@UiHandler("deleteKVPPConfigButton")
	public void deleteButtonClickHandler(ClickEvent event) {
		presenter.onDeleteKVPPClicked();
	}

	/**
	 * To create KV Page Process List.
	 * 
	 * @param fields Collection<KVPageProcessDTO>
	 */
	public void createKVPageProcessList(Collection<KVPageProcessDTO> fields) {
		List<Record> recordList = (List<Record>) setKVPageProcessList(fields);
		kvPPConfigListView.listView.initTable(recordList.size(), null, recordList, true, false, presenter, null, false);
	}

	/**
	 * To set KV Page Process List.
	 * 
	 * @param fields Collection<KVPageProcessDTO>
	 * @return Collection<Record>
	 */
	public Collection<Record> setKVPageProcessList(Collection<KVPageProcessDTO> fields) {
		List<Record> recordList = new LinkedList<Record>();
		if (fields != null && !fields.isEmpty()) {
			for (final KVPageProcessDTO kvPageProcessDTO : fields) {
				Record record = new Record(String.valueOf(kvPageProcessDTO.getIdentifier()));
				record.addWidget(kvPPConfigListView.keyPattern, new Label(kvPageProcessDTO.getKeyPattern()));
				record.addWidget(kvPPConfigListView.location, new Label(kvPageProcessDTO.getLocationType().name()));
				record.addWidget(kvPPConfigListView.valuePattern, new Label(kvPageProcessDTO.getValuePattern()));
				record.addWidget(kvPPConfigListView.noOfWords, new Label(kvPageProcessDTO.getNoOfWords().toString()));
				record.addWidget(kvPPConfigListView.pageLevelFieldName, new Label(kvPageProcessDTO.getPageLevelFieldName()));
				recordList.add(record);
			}
		}
		return recordList;
	}

	/**
	 * To get Kv PP Config List View.
	 * 
	 * @return KV_PP_ConfigListView
	 */
	public KV_PP_ConfigListView getKvPPConfigListView() {
		return kvPPConfigListView;
	}

	/**
	 * To get Add KV PP Configuration Button.
	 * 
	 * @return Button
	 */
	public Button getAddKVPPConfigButton() {
		return addKVPPConfigButton;
	}

	/**
	 * To get Edit KV PP Configuration Button.
	 * 
	 * @return Button
	 */
	public Button getEditKVPPConfigButton() {
		return editKVPPConfigButton;
	}

	/**
	 * To get Delete KV PP Configuration Button.
	 * 
	 * @return Button
	 */
	public Button getDeleteKVPPConfigButton() {
		return deleteKVPPConfigButton;
	}
}
