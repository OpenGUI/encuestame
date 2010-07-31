/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.utils.web;

import java.io.Serializable;

/**
 * Unit Hash Tag.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 31, 2010 7:49:45 AM
 * @version $Id:$
 */
public class UnitHashTag implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -56061660495067820L;

    /** Name. **/
    private String hashTagName;

    /** Id. **/
    private Long id;

    /** Constructor. **/
    public UnitHashTag() {}


    /** Constructor. **/
    public UnitHashTag(String hashTagName) {
        super();
        this.hashTagName = hashTagName;
    }

    /**
     * @return the hashTagName
     */
    public final String getHashTagName() {
        return hashTagName;
    }

    /**
     * @param hashTagName the hashTagName to set
     */
    public final void setHashTagName(String hashTagName) {
        this.hashTagName = hashTagName;
    }

    /**
     * @return the id
     */
    public final Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public final void setId(Long id) {
        this.id = id;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((hashTagName == null) ? 0 : hashTagName.hashCode());
        return result;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UnitHashTag other = (UnitHashTag) obj;
        if (hashTagName == null) {
            if (other.hashTagName != null)
                return false;
        } else if (!hashTagName.equals(other.hashTagName))
            return false;
        return true;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(
                getHashTagName());
        return builder.toString();
    }
}
