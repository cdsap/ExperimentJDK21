/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid

import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters.None
import org.gradle.process.ExecOperations
import org.gradle.process.internal.ExecException
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import javax.inject.Inject

abstract class Test : ValueSource<String, None> {


    @get:Inject
    abstract val execOperations: ExecOperations

    override fun obtain(): String {
        val output = ByteArrayOutputStream()
        val error = ByteArrayOutputStream()
        return try {
            execOperations.exec {
                try {
                    commandLine("sh", "-c", "echo \$((1 + \$RANDOM % 10))")
                    standardOutput = output
                    errorOutput = error
                } catch (e: Exception) {
                }
            }
            String(output.toByteArray(), Charset.defaultCharset())
        } catch (e: ExecException) {
            ""
        }
    }
}
