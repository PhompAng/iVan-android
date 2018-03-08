package com.firebaseapp.ivan.ivan.delegate

import com.firebaseapp.ivan.ivan.model.Parent
import com.firebaseapp.ivan.ivan.model.Student

/**
 * @author phompang on 20/2/2018 AD.
 */
data class DelegateStudent(var student: Student = Student(),
						   var parent: Parent = Parent())