/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.core.persistence.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * List Email Catalog.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  June 20, 2010
 * @version $Id: $
 */

@Entity
@Table(name = "cat_list_emails")
public class CatListEmails {

	private Long idList;
	private SecUsers usuarioEmail;
	private Date createdAt;
	private String listName;

	/**
	 * @return the idList
	 */
 	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_list", unique = true, nullable = false)
 	public Long getIdList() {
		return idList;
	}

	/**
	 * @param idList the idList to set
	 */
	public void setIdList(Long idList) {
		this.idList = idList;
	}

	/**
	 * @return the usuarioEmail
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "uid", nullable = false)
	public SecUsers getUsuarioEmail() {
		return usuarioEmail;
	}

	/**
	 * @param usuarioEmail the usuarioEmail to set
	 */
	public void setUsuarioEmail(SecUsers usuarioEmail) {
		this.usuarioEmail = usuarioEmail;
	}

	/**
	 * @return the createdAt
	 */
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "createdAt", nullable = true)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the listName
	 */
	@Column(name = "list_name", nullable = true)
	public String getListName() {
		return listName;
	}

	/**
	 * @param listName the listName to set
	 */
	public void setListName(String listName) {
		this.listName = listName;
	}
}
