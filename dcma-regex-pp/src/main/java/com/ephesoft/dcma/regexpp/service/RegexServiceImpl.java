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

package com.ephesoft.dcma.regexpp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.ephesoft.dcma.core.DCMAException;
import com.ephesoft.dcma.core.annotation.PostProcess;
import com.ephesoft.dcma.core.annotation.PreProcess;
import com.ephesoft.dcma.da.id.BatchInstanceID;
import com.ephesoft.dcma.da.service.BatchInstanceService;
import com.ephesoft.dcma.regexpp.RegexReader;
import com.ephesoft.dcma.util.ApplicationContextUtil;
import com.ephesoft.dcma.util.BackUpFileService;

/**
 * This service is used to read Key Value Pattern from an image file and writes the value, coordinates and confidence score in page
 * level Fields inside batch.xml. Confidence score is decided on the basis of key value i.e. 100 if key and value is found and 0 if not
 * found.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.barcode.service.RegexService
 * 
 */
public class RegexServiceImpl implements RegexService {

	/**
	 * LOGGER to print the logging information.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RegexServiceImpl.class);

	/**
	 * regexReader {@link RegexReader}.
	 */
	@Autowired
	private transient RegexReader regexReader;

	/**
	 * Instance of {@link BatchInstanceService}.
	 */
	@Autowired
	private BatchInstanceService batchInstanceService;

	/**
	 * Pre-processing work.
	 * 
	 * @param batchInstanceID {@link BatchInstanceID}
	 * @param pluginWorkflow {@link String}
	 */
	@PreProcess
	public void preProcess(final BatchInstanceID batchInstanceID, String pluginWorkflow) {
		Assert.notNull(batchInstanceID);
		final String batchInstanceIdentifier = batchInstanceID.getID();
		BackUpFileService.backUpBatch(batchInstanceIdentifier, batchInstanceService
				.getSystemFolderForBatchInstanceId(batchInstanceIdentifier));
	}

	/**
	 * Post-processing work.
	 * 
	 * @param batchInstanceID {@link BatchInstanceID}
	 * @param pluginWorkflow {@link String}
	 */
	@PostProcess
	public void postProcess(final BatchInstanceID batchInstanceID, String pluginWorkflow) {
		Assert.notNull(batchInstanceID);
		final String batchInstanceIdentifier = batchInstanceID.getID();
		BackUpFileService.backUpBatch(batchInstanceIdentifier, pluginWorkflow, batchInstanceService
				.getSystemFolderForBatchInstanceId(batchInstanceIdentifier));
	}

	/**
	 * This method extracts the Key values pattern, coordinates and confidence for each image file and updates the batch.xml for those
	 * images respectively.
	 * 
	 * @param batchInstanceID {@link BatchInstanceID}
	 * @param pluginWorkflow {@link String}
	 * @throws DCMAException if any error occur in reading and updating batch.xml
	 */
	@Override
	public void regexPPReader(final BatchInstanceID batchInstanceID, final String pluginWorkflow) throws DCMAException {
		try {
			LOGGER.info("batchInstanceId : " + batchInstanceID.getID());
			regexReader.readRegex(batchInstanceID);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new DCMAException(e.getMessage(), e);
		}
	}

	/**
	 * Main method.
	 * 
	 * @param args String[]
	 * @throws DCMAException in case error occurs
	 */
	public static void main(String[] args) throws DCMAException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:/META-INF/applicationContext-regex-pp.xml");
		context.start();
		RegexService regexService = ApplicationContextUtil.getSingleBeanOfType(context, RegexService.class);
		BatchInstanceID batchInstanceID = new BatchInstanceID("BI8");
		regexService.regexPPReader(batchInstanceID, "abc");
	}

}
