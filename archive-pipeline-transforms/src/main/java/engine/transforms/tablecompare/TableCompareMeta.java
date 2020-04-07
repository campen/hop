/*! ******************************************************************************
 *
 * Hop : The Hop Orchestration Platform
 *
 * http://www.project-hop.org
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.apache.hop.pipeline.transforms.tablecompare;

import org.apache.hop.core.CheckResult;
import org.apache.hop.core.CheckResultInterface;
import org.apache.hop.core.database.DatabaseMeta;
import org.apache.hop.core.exception.HopTransformException;
import org.apache.hop.core.exception.HopXMLException;
import org.apache.hop.core.row.IRowMeta;
import org.apache.hop.core.row.IValueMeta;
import org.apache.hop.core.row.value.ValueMetaInteger;
import org.apache.hop.core.util.Utils;
import org.apache.hop.core.variables.iVariables;
import org.apache.hop.core.xml.XMLHandler;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.metastore.api.IMetaStore;
import org.apache.hop.pipeline.Pipeline;
import org.apache.hop.pipeline.PipelineMeta;
import org.apache.hop.pipeline.transform.BaseTransformMeta;
import org.apache.hop.pipeline.transform.ITransformData;
import org.apache.hop.pipeline.transform.ITransform;
import org.apache.hop.pipeline.transform.TransformMeta;
import org.apache.hop.pipeline.transform.ITransform;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

//@Transform(
//    id = "TableCompare",
//    image = "be/kjube/plugins/images/table_compare.png",
//    description ="kJube.Plugins.TableCompare.Description",
//    name = "kJube.Plugins.TableCompare.Name",
//    categoryDescription="kJube.Category.Name",
//    i18nPackageName="be.kjube.plugins"
//  )
public class TableCompareMeta extends BaseTransformMeta implements ITransform {
  private static Class<?> PKG = TableCompare.class; // for i18n purposes, needed by Translator!!

  private DatabaseMeta referenceConnection;
  private String referenceSchemaField;
  private String referenceTableField;

  private DatabaseMeta compareConnection;
  private String compareSchemaField;
  private String compareTableField;

  private String keyFieldsField;
  private String excludeFieldsField;

  private String nrErrorsField;
  private String nrRecordsReferenceField;
  private String nrRecordsCompareField;
  private String nrErrorsLeftJoinField;
  private String nrErrorsInnerJoinField;
  private String nrErrorsRightJoinField;

  private String keyDescriptionField;
  private String valueReferenceField;
  private String valueCompareField;
  private IMetaStore metaStore;

  public TableCompareMeta() {
    super(); // allocate BaseTransformMeta
  }

  /**
   * @return the referenceSchemaField
   */
  public String getReferenceSchemaField() {
    return referenceSchemaField;
  }

  /**
   * @param referenceSchemaField the referenceSchemaField to set
   */
  public void setReferenceSchemaField( String referenceSchemaField ) {
    this.referenceSchemaField = referenceSchemaField;
  }

  /**
   * @return the referenceTableField
   */
  public String getReferenceTableField() {
    return referenceTableField;
  }

  /**
   * @param referenceTableField the referenceTableField to set
   */
  public void setReferenceTableField( String referenceTableField ) {
    this.referenceTableField = referenceTableField;
  }

  /**
   * @return the compareSchemaField
   */
  public String getCompareSchemaField() {
    return compareSchemaField;
  }

  /**
   * @param compareSchemaField the compareSchemaField to set
   */
  public void setCompareSchemaField( String compareSchemaField ) {
    this.compareSchemaField = compareSchemaField;
  }

  /**
   * @return the compareTableField
   */
  public String getCompareTableField() {
    return compareTableField;
  }

  /**
   * @param compareTableField the compareTableField to set
   */
  public void setCompareTableField( String compareTableField ) {
    this.compareTableField = compareTableField;
  }

  /**
   * @return the nrErrorsField
   */
  public String getNrErrorsField() {
    return nrErrorsField;
  }

  /**
   * @param nrErrorsField the nrErrorsField to set
   */
  public void setNrErrorsField( String nrErrorsField ) {
    this.nrErrorsField = nrErrorsField;
  }

  /**
   * @return the referenceConnection
   */
  public DatabaseMeta getReferenceConnection() {
    return referenceConnection;
  }

  /**
   * @param referenceConnection the referenceConnection to set
   */
  public void setReferenceConnection( DatabaseMeta referenceConnection ) {
    this.referenceConnection = referenceConnection;
  }

  /**
   * @return the compareConnection
   */
  public DatabaseMeta getCompareConnection() {
    return compareConnection;
  }

  /**
   * @param compareConnection the compareConnection to set
   */
  public void setCompareConnection( DatabaseMeta compareConnection ) {
    this.compareConnection = compareConnection;
  }

  @Override
  public DatabaseMeta[] getUsedDatabaseConnections() {
    List<DatabaseMeta> connList = new ArrayList<DatabaseMeta>( 2 );
    if ( compareConnection != null ) {
      connList.add( compareConnection );
    }
    if ( referenceConnection != null ) {
      connList.add( referenceConnection );
    }
    if ( connList.size() > 0 ) {
      DatabaseMeta[] rtn = new DatabaseMeta[ connList.size() ];
      connList.toArray( rtn );
      return rtn;
    } else {
      return super.getUsedDatabaseConnections();
    }
  }

  /**
   * @return the keyFieldsField
   */
  public String getKeyFieldsField() {
    return keyFieldsField;
  }

  /**
   * @param keyFieldsField the keyFieldsField to set
   */
  public void setKeyFieldsField( String keyFieldsField ) {
    this.keyFieldsField = keyFieldsField;
  }

  /**
   * @return the excludeFieldsField
   */
  public String getExcludeFieldsField() {
    return excludeFieldsField;
  }

  /**
   * @param excludeFieldsField the excludeFieldsField to set
   */
  public void setExcludeFieldsField( String excludeFieldsField ) {
    this.excludeFieldsField = excludeFieldsField;
  }

  /**
   * @return the nrRecordsReferenceField
   */
  public String getNrRecordsReferenceField() {
    return nrRecordsReferenceField;
  }

  /**
   * @param nrRecordsReferenceField the nrRecordsReferenceField to set
   */
  public void setNrRecordsReferenceField( String nrRecordsReferenceField ) {
    this.nrRecordsReferenceField = nrRecordsReferenceField;
  }

  /**
   * @return the nrRecordsCompareField
   */
  public String getNrRecordsCompareField() {
    return nrRecordsCompareField;
  }

  /**
   * @param nrRecordsCompareField the nrRecordsCompareField to set
   */
  public void setNrRecordsCompareField( String nrRecordsCompareField ) {
    this.nrRecordsCompareField = nrRecordsCompareField;
  }

  /**
   * @return the nrErrorsLeftJoinField
   */
  public String getNrErrorsLeftJoinField() {
    return nrErrorsLeftJoinField;
  }

  /**
   * @param nrErrorsLeftJoinField the nrErrorsLeftJoinField to set
   */
  public void setNrErrorsLeftJoinField( String nrErrorsLeftJoinField ) {
    this.nrErrorsLeftJoinField = nrErrorsLeftJoinField;
  }

  /**
   * @return the nrErrorsInnerJoinField
   */
  public String getNrErrorsInnerJoinField() {
    return nrErrorsInnerJoinField;
  }

  /**
   * @param nrErrorsInnerJoinField the nrErrorsInnerJoinField to set
   */
  public void setNrErrorsInnerJoinField( String nrErrorsInnerJoinField ) {
    this.nrErrorsInnerJoinField = nrErrorsInnerJoinField;
  }

  /**
   * @return the nrErrorsRightJoinField
   */
  public String getNrErrorsRightJoinField() {
    return nrErrorsRightJoinField;
  }

  /**
   * @param nrErrorsRightJoinField the nrErrorsRightJoinField to set
   */
  public void setNrErrorsRightJoinField( String nrErrorsRightJoinField ) {
    this.nrErrorsRightJoinField = nrErrorsRightJoinField;
  }

  /**
   * @return the keyDescriptionField
   */
  public String getKeyDescriptionField() {
    return keyDescriptionField;
  }

  /**
   * @param keyDescriptionField the keyDescriptionField to set
   */
  public void setKeyDescriptionField( String keyDescriptionField ) {
    this.keyDescriptionField = keyDescriptionField;
  }

  /**
   * @return the valueReferenceField
   */
  public String getValueReferenceField() {
    return valueReferenceField;
  }

  /**
   * @param valueReferenceField the valueReferenceField to set
   */
  public void setValueReferenceField( String valueReferenceField ) {
    this.valueReferenceField = valueReferenceField;
  }

  /**
   * @return the valueCompareField
   */
  public String getValueCompareField() {
    return valueCompareField;
  }

  /**
   * @param valueCompareField the valueCompareField to set
   */
  public void setValueCompareField( String valueCompareField ) {
    this.valueCompareField = valueCompareField;
  }

  @Override
  public void loadXML( Node transformNode, IMetaStore metaStore ) throws HopXMLException {
    readData( transformNode, metaStore );
  }

  @Override
  public Object clone() {
    TableCompareMeta retval = (TableCompareMeta) super.clone();

    return retval;
  }

  @Override
  public void getFields( IRowMeta inputRowMeta, String origin, IRowMeta[] info, TransformMeta nextTransform,
                         iVariables variables, IMetaStore metaStore ) throws HopTransformException {

    if ( Utils.isEmpty( nrErrorsField ) ) {
      throw new HopTransformException( BaseMessages.getString(
        PKG, "TableCompareMeta.Exception.NrErrorsFieldIsNotSpecified" ) );
    }
    if ( Utils.isEmpty( nrRecordsReferenceField ) ) {
      throw new HopTransformException( BaseMessages.getString(
        PKG, "TableCompareMeta.Exception.NrRecordsReferenceFieldNotSpecified" ) );
    }
    if ( Utils.isEmpty( nrRecordsCompareField ) ) {
      throw new HopTransformException( BaseMessages.getString(
        PKG, "TableCompareMeta.Exception.NrRecordsCompareFieldNotSpecified" ) );
    }
    if ( Utils.isEmpty( nrErrorsLeftJoinField ) ) {
      throw new HopTransformException( BaseMessages.getString(
        PKG, "TableCompareMeta.Exception.NrErrorsLeftJoinFieldNotSpecified" ) );
    }
    if ( Utils.isEmpty( nrErrorsInnerJoinField ) ) {
      throw new HopTransformException( BaseMessages.getString(
        PKG, "TableCompareMeta.Exception.NrErrorsInnerJoinFieldNotSpecified" ) );
    }
    if ( Utils.isEmpty( nrErrorsRightJoinField ) ) {
      throw new HopTransformException( BaseMessages.getString(
        PKG, "TableCompareMeta.Exception.NrErrorsRightJoinFieldNotSpecified" ) );
    }

    IValueMeta nrErrorsValueMeta = new ValueMetaInteger( nrErrorsField );
    nrErrorsValueMeta.setLength( 9 );
    nrErrorsValueMeta.setOrigin( origin );
    inputRowMeta.addValueMeta( nrErrorsValueMeta );

    IValueMeta nrRecordsReference =
      new ValueMetaInteger( nrRecordsReferenceField );
    nrRecordsReference.setLength( 9 );
    nrRecordsReference.setOrigin( origin );
    inputRowMeta.addValueMeta( nrRecordsReference );

    IValueMeta nrRecordsCompare = new ValueMetaInteger( nrRecordsCompareField );
    nrRecordsCompare.setLength( 9 );
    nrRecordsCompare.setOrigin( origin );
    inputRowMeta.addValueMeta( nrRecordsCompare );

    IValueMeta nrErrorsLeft = new ValueMetaInteger( nrErrorsLeftJoinField );
    nrErrorsLeft.setLength( 9 );
    nrErrorsLeft.setOrigin( origin );
    inputRowMeta.addValueMeta( nrErrorsLeft );

    IValueMeta nrErrorsInner = new ValueMetaInteger( nrErrorsInnerJoinField );
    nrErrorsInner.setLength( 9 );
    nrErrorsInner.setOrigin( origin );
    inputRowMeta.addValueMeta( nrErrorsInner );

    IValueMeta nrErrorsRight = new ValueMetaInteger( nrErrorsRightJoinField );
    nrErrorsRight.setLength( 9 );
    nrErrorsRight.setOrigin( origin );
    inputRowMeta.addValueMeta( nrErrorsRight );
  }

  private void readData( Node transformNode, IMetaStore metaStore ) throws HopXMLException {
    this.metaStore = metaStore;
    try {
      referenceConnection =
        DatabaseMeta.loadDatabase( metaStore, XMLHandler.getTagValue( transformNode, "reference_connection" ) );
      referenceSchemaField = XMLHandler.getTagValue( transformNode, "reference_schema_field" );
      referenceTableField = XMLHandler.getTagValue( transformNode, "reference_table_field" );

      compareConnection =
        DatabaseMeta.loadDatabase( metaStore, XMLHandler.getTagValue( transformNode, "compare_connection" ) );
      compareSchemaField = XMLHandler.getTagValue( transformNode, "compare_schema_field" );
      compareTableField = XMLHandler.getTagValue( transformNode, "compare_table_field" );

      keyFieldsField = XMLHandler.getTagValue( transformNode, "key_fields_field" );
      excludeFieldsField = XMLHandler.getTagValue( transformNode, "exclude_fields_field" );
      nrErrorsField = XMLHandler.getTagValue( transformNode, "nr_errors_field" );

      nrRecordsReferenceField = XMLHandler.getTagValue( transformNode, "nr_records_reference_field" );
      nrRecordsCompareField = XMLHandler.getTagValue( transformNode, "nr_records_compare_field" );
      nrErrorsLeftJoinField = XMLHandler.getTagValue( transformNode, "nr_errors_left_join_field" );
      nrErrorsInnerJoinField = XMLHandler.getTagValue( transformNode, "nr_errors_inner_join_field" );
      nrErrorsRightJoinField = XMLHandler.getTagValue( transformNode, "nr_errors_right_join_field" );

      keyDescriptionField = XMLHandler.getTagValue( transformNode, "key_description_field" );
      valueReferenceField = XMLHandler.getTagValue( transformNode, "value_reference_field" );
      valueCompareField = XMLHandler.getTagValue( transformNode, "value_compare_field" );

    } catch ( Exception e ) {
      throw new HopXMLException( "It was not possibke to load the Trim metadata from XML", e );
    }
  }

  @Override
  public String getXML() {
    StringBuilder retval = new StringBuilder();

    retval.append( "      " ).append(
      XMLHandler.addTagValue( "reference_connection", referenceConnection == null ? null : referenceConnection
        .getName() ) );
    retval.append( "      " ).append( XMLHandler.addTagValue( "reference_schema_field", referenceSchemaField ) );
    retval.append( "      " ).append( XMLHandler.addTagValue( "reference_table_field", referenceTableField ) );

    retval.append( "      " ).append(
      XMLHandler.addTagValue( "compare_connection", compareConnection == null ? null : compareConnection
        .getName() ) );
    retval.append( "      " ).append( XMLHandler.addTagValue( "compare_schema_field", compareSchemaField ) );
    retval.append( "      " ).append( XMLHandler.addTagValue( "compare_table_field", compareTableField ) );

    retval.append( "      " ).append( XMLHandler.addTagValue( "key_fields_field", keyFieldsField ) );
    retval.append( "      " ).append( XMLHandler.addTagValue( "exclude_fields_field", excludeFieldsField ) );
    retval.append( "      " ).append( XMLHandler.addTagValue( "nr_errors_field", nrErrorsField ) );

    retval.append( "      " ).append(
      XMLHandler.addTagValue( "nr_records_reference_field", nrRecordsReferenceField ) );
    retval.append( "      " ).append( XMLHandler.addTagValue( "nr_records_compare_field", nrRecordsCompareField ) );
    retval
      .append( "      " ).append( XMLHandler.addTagValue( "nr_errors_left_join_field", nrErrorsLeftJoinField ) );
    retval.append( "      " ).append(
      XMLHandler.addTagValue( "nr_errors_inner_join_field", nrErrorsInnerJoinField ) );
    retval.append( "      " ).append(
      XMLHandler.addTagValue( "nr_errors_right_join_field", nrErrorsRightJoinField ) );

    retval.append( "      " ).append( XMLHandler.addTagValue( "key_description_field", keyDescriptionField ) );
    retval.append( "      " ).append( XMLHandler.addTagValue( "value_reference_field", valueReferenceField ) );
    retval.append( "      " ).append( XMLHandler.addTagValue( "value_compare_field", valueCompareField ) );

    return retval.toString();
  }

  @Override
  public void setDefault() {
    nrErrorsField = "nrErrors";
    nrRecordsReferenceField = "nrRecordsReferenceTable";
    nrRecordsCompareField = "nrRecordsCompareTable";
    nrErrorsLeftJoinField = "nrErrorsLeftJoin";
    nrErrorsInnerJoinField = "nrErrorsInnerJoin";
    nrErrorsRightJoinField = "nrErrorsRightJoin";
  }

  @Override
  public void check( List<CheckResultInterface> remarks, PipelineMeta pipelineMeta, TransformMeta transformMeta,
                     IRowMeta prev, String[] input, String[] output, IRowMeta info, iVariables variables,
                     IMetaStore metaStore ) {
    CheckResult cr;
    if ( prev == null || prev.size() == 0 ) {
      cr =
        new CheckResult( CheckResult.TYPE_RESULT_WARNING, BaseMessages.getString(
          PKG, "IfNullMeta.CheckResult.NotReceivingFields" ), transformMeta );
      remarks.add( cr );
    } else {
      cr =
        new CheckResult( CheckResult.TYPE_RESULT_OK, BaseMessages.getString(
          PKG, "IfNullMeta.CheckResult.TransformRecevingData", prev.size() + "" ), transformMeta );
      remarks.add( cr );
    }

    // See if we have input streams leading to this transform!
    if ( input.length > 0 ) {
      cr =
        new CheckResult( CheckResult.TYPE_RESULT_OK, BaseMessages.getString(
          PKG, "IfNullMeta.CheckResult.TransformRecevingData2" ), transformMeta );
      remarks.add( cr );
    } else {
      cr =
        new CheckResult( CheckResult.TYPE_RESULT_ERROR, BaseMessages.getString(
          PKG, "IfNullMeta.CheckResult.NoInputReceivedFromOtherTransforms" ), transformMeta );
      remarks.add( cr );
    }
  }

  @Override
  public ITransform getTransform( TransformMeta transformMeta, ITransformData data, int cnr, PipelineMeta tr,
                                Pipeline pipeline ) {
    return new TableCompare( transformMeta, this, data, cnr, tr, pipeline );
  }

  @Override
  public ITransformData getTransformData() {
    return new TableCompareData();
  }

  @Override
  public boolean supportsErrorHandling() {
    return true;
  }

}