package com.waadsoft.ecommerce.common.core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * @param <ID> The type of the identifier of the entity(e.g.: String, Integer,
 * Long, ...)
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AbstractPersistable<ID> implements Serializable {

    @Version
    @Column(name = "VERSION")
    @XmlTransient
    private Short version;

    @XmlTransient
    public Short getVersion() {
        return version;
    }

    protected void setVersion(Short version) {
        this.version = version;
    }
}

/*=============================================================================
 * Copyright 2023 Waad Soft<https://www.waadsoft.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 =============================================================================*/
