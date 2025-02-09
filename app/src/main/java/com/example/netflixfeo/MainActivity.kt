package com.example.netflixfeo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.netflixfeo.modelo.Usuario
import com.example.netflixfeo.ui.PeliculasApp
import com.example.netflixfeo.ui.theme.NetflixFeoTheme
import kotlin.reflect.KMutableProperty1

import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NetflixFeoTheme {
                PeliculasApp()
            }
        }
    }
}*/
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val persona = Persona("Hola", 1, true)
            GenericEditorScreen(persona) { updatedPersona ->
                println(updatedPersona)
            }
        }
    }
}

data class Persona(var nombre: String, var edad: Int, var activo:Boolean)
@Composable
fun <T : Any> GenericEditorScreen(obj: T, onSave: (T) -> Unit) {
    var editableObject by remember { mutableStateOf(obj) }

    val immutableProperties = obj::class.memberProperties.filter { it !is KMutableProperty1<*, *> }
    val mutableProperties = obj::class.memberProperties.filterIsInstance<KMutableProperty1<T, *>>()

    Column(modifier = Modifier.padding(16.dp)) {
        // Mostrar `val` como texto solo lectura
        immutableProperties.forEach { property ->
            ReadOnlyField(
                label = getLocalizedPropertyName(property.name),
                value = property.call(obj)?.toString() ?: ""
            )
        }

        // Mostrar `var` con `TextField` o `Switch` si es Boolean
        mutableProperties.forEach { property ->
            when (property.returnType.classifier) {
                Boolean::class -> {
                    var value by remember { mutableStateOf(property.get(editableObject) as Boolean) }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(getLocalizedPropertyName(property.name))
                        Switch(
                            checked = value,
                            onCheckedChange = {
                                value = it
                                property.setter.call(editableObject, it)
                            }
                        )
                    }
                }

                else -> {
                    var value by remember { mutableStateOf(property.get(editableObject)?.toString() ?: "") }

                    TextField(
                        value = value,
                        onValueChange = {
                            value = it
                            property.setter.call(editableObject, parseValue(it, property))
                        },
                        label = { Text(getLocalizedPropertyName(property.name)) },
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    )
                }
            }
        }

        Button(
            onClick = { onSave(editableObject) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(stringResource(id = R.string.button_save))
        }
    }
}

// Función para obtener el nombre traducido de una propiedad
@Composable
fun getLocalizedPropertyName(propertyName: String): String {
    return when (propertyName) {
        "id" -> stringResource(id = R.string.label_id)
        "nombre" -> stringResource(id = R.string.label_nombre)
        "edad" -> stringResource(id = R.string.label_edad)
        "activo" -> stringResource(id = R.string.label_activo)
        else -> propertyName
    }
}

// Composable para mostrar valores `val` sin edición
@Composable
fun ReadOnlyField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
// Función para convertir String al tipo correcto
fun <T : Any> parseValue(value: String, property: KMutableProperty1<T, *>): Any? {
    return when (property.returnType.classifier) {
        Int::class -> value.toIntOrNull()
        Double::class -> value.toDoubleOrNull()
        else -> value
    }
}
