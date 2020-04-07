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

package org.apache.hop.workflow.actions.success;

import org.apache.hop.core.ICheckResult;
import org.apache.hop.core.Result;
import org.apache.hop.core.annotations.Action;
import org.apache.hop.core.exception.HopXMLException;
import org.apache.hop.core.variables.IVariables;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.workflow.WorkflowMeta;
import org.apache.hop.workflow.action.IAction;
import org.apache.hop.workflow.action.ActionBase;
import org.apache.hop.metastore.api.IMetaStore;
import org.w3c.dom.Node;

import java.util.List;

/**
 * Action type to success a workflow.
 *
 * @author Samatar
 * @since 12-02-2007
 */

@Action(
  id = "SUCCESS",
  i18nPackageName = "org.apache.hop.workflow.actions.success",
  name = "ActionSuccess.Name",
  description = "ActionSuccess.Description",
  image = "Success.svg",
  categoryDescription = "i18n:org.apache.hop.workflow:ActionCategory.Category.General"
)	 
public class ActionSuccess extends ActionBase implements Cloneable, IAction {
  private static Class<?> PKG = ActionSuccess.class; // for i18n purposes, needed by Translator!!

  public ActionSuccess( String n, String scr ) {
    super( n, "" );
  }

  public ActionSuccess() {
    this( "", "" );
  }

  public Object clone() {
    ActionSuccess je = (ActionSuccess) super.clone();
    return je;
  }

  public String getXML() {
    StringBuilder retval = new StringBuilder();

    retval.append( super.getXML() );

    return retval.toString();
  }

  public void loadXML( Node entrynode,
                       IMetaStore metaStore ) throws HopXMLException {
    try {
      super.loadXML( entrynode );
    } catch ( Exception e ) {
      throw new HopXMLException( BaseMessages.getString( PKG, "ActionSuccess.Meta.UnableToLoadFromXML" ), e );
    }
  }

  /**
   * Execute this action and return the result. In this case it means, just set the result boolean in the Result
   * class.
   *
   * @param previousResult The result of the previous execution
   * @return The Result of the execution.
   */
  public Result execute( Result previousResult, int nr ) {
    Result result = previousResult;
    result.setNrErrors( 0 );
    result.setResult( true );

    return result;
  }

  public boolean evaluates() {
    return true;
  }

  public boolean isUnconditional() {
    return false;
  }

  @Override
  public void check( List<ICheckResult> remarks, WorkflowMeta workflowMeta, IVariables variables,
                     IMetaStore metaStore ) {

  }

}