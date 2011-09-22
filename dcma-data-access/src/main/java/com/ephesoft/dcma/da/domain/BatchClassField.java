/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2011 Ephesoft Inc. 
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

package com.ephesoft.dcma.da.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ephesoft.dcma.core.EphesoftProperty;
import com.ephesoft.dcma.core.common.DataType;
import com.ephesoft.dcma.core.model.common.AbstractChangeableEntity;

@Entity
@Table(name = "batch_class_field")
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class BatchClassField extends AbstractChangeableEntity implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "batch_class_id")
	private BatchClass batchClass;

	@Column(name = "field_data_type")
	@Enumerated(EnumType.STRING)
	private DataType dataType;

	@Column(name = "identifier")
	private String identifier;

	@Column(name = "field_type_name")
	private String name;

	@Column(name = "field_order_number", nullable = false)
	private int fieldOrderNumber;

	@Column(name = "field_type_description")
	private String description;

	@Column(name = "validation_pattern")
	private String validationPattern;

	@Column(name = "sample_value")
	private String sampleValue;

	@Column(name = "field_option_value_list")
	private String fieldOptionValueList;
	
	private String value;

	public BatchClass getBatchClass() {
		return batchClass;
	}

	public void setBatchClass(BatchClass batchClass) {
		this.batchClass = batchClass;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValidationPattern() {
		return validationPattern;
	}

	public void setValidationPattern(String validationPattern) {
		this.validationPattern = validationPattern;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public int getFieldOrderNumber() {
		return fieldOrderNumber;
	}

	public void setFieldOrderNumber(int fieldOrderNumber) {
		this.fieldOrderNumber = fieldOrderNumber;
	}

	public String getSampleValue() {
		return sampleValue;
	}

	public void setSampleValue(String sampleValue) {
		this.sampleValue = sampleValue;
	}

	public String getFieldOptionValueList() {
		return fieldOptionValueList;
	}

	public void setFieldOptionValueList(String fieldOptionValueList) {
		this.fieldOptionValueList = fieldOptionValueList;
	}
	
	public String getValue() {
		return value;
	}

	
	public void setValue(String value) {
		this.value = value;
	}

	public void postPersist() {
		this.identifier = EphesoftProperty.BATCH_CLASS_FIELD.getProperty() + Long.toHexString(this.getId()).toUpperCase();
	}

}
