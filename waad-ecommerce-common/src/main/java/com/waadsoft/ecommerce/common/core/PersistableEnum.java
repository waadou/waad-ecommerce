package com.waadsoft.ecommerce.common.core;

import java.io.Serializable;

/**
 * Functional interface implemented by enum types.
 *
 * <p>
 * Thanks to the JPA converter, the value of {@link #getValue()} of the enum
 * constant is stored into the database.
 *
 * <pre>
 *
 * public enum AccountStatus implements PersistableEnum &lt;String&gt; {
 *      NEW("N"),
 *      ACTIVE("A"),
 *      LOCKED("L"),
 *      DELETED("X");
 *
 *      private String value;
 *
 *      private AccountStatus(String value){
 *          this.value = value;
 *      }
 *
 *      public String getValue(){
 *          return value;
 *      }
 *
 *      &#064;javax.persistence.Converter(autoApply = true)
 *      public static class Converter extends PersistableEnumConverter &lt;AccountStatus, String&gt; {
 *          public Converter() {
 *              super(AccountStatus.class);
 *          }
 *      }
 * }
 *
 * This allows when in an entity, to declare an attribute of type AccountStatus and not annotated
 * it with <strong>&#064;Enumerated(EnumType.STRING)</strong> and even use <strong>&#064;Column(length=1)</strong>:
 *      ...
 *
 *      &#064;Column(name="STATUS", length=1, nullable = false)
 *      private AccountStatus status;
 *
 *      ...
 * </pre>
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 * @param <E> Type of Database Column. Enum constants will be converted to this
 * type and saved into the database.
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
