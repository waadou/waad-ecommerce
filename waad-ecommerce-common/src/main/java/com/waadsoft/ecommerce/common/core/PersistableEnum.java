package com.waadsoft.ecommerce.common.core;

import java.io.Serializable;

/**
 * Functional interface that enum must implement.
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 * @param <E> Type of Database Column.
 *
 * @see PersistableEnumConverter
 * @see
 * <a href="https://stackoverflow.com/questions/23564506/is-it-possible-to-write-a-generic-enum-converter-for-jpa">Is
 * it possible to write a generic enum converter for JPA?</a>
 */
@FunctionalInterface
public interface PersistableEnum<E extends Serializable> {

    /**
     * Returns the converted value for the enum constant to be stored in the
     * database column.
     *
     * @return The converted value for the enum constant to be stored in the
     * database column.
     */
    E getValue();
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
