package com.example.lecture5.data.provider

import com.example.lecture5.model.ActivityStartInfo
import com.example.lecture5.presentation.activities.single.instance.SingleInstanceA
import com.example.lecture5.presentation.activities.single.instance.SingleInstanceB
import com.example.lecture5.presentation.activities.single.instance.SingleInstanceC
import com.example.lecture5.presentation.activities.single.instance.SingleInstanceD
import com.example.lecture5.presentation.activities.single.instance.affinity.SingleInstanceAffinityA
import com.example.lecture5.presentation.activities.single.instance.affinity.SingleInstanceAffinityB
import com.example.lecture5.presentation.activities.single.instance.affinity.SingleInstanceAffinityC
import com.example.lecture5.presentation.activities.single.instance.affinity.SingleInstanceAffinityD
import com.example.lecture5.presentation.activities.single.instance.pertask.SingleInstancePerTaskA
import com.example.lecture5.presentation.activities.single.instance.pertask.SingleInstancePerTaskB
import com.example.lecture5.presentation.activities.single.instance.pertask.SingleInstancePerTaskC
import com.example.lecture5.presentation.activities.single.instance.pertask.SingleInstancePerTaskD
import com.example.lecture5.presentation.activities.single.instance.pertask.affinity.SingleInstancePerTaskAffinityA
import com.example.lecture5.presentation.activities.single.instance.pertask.affinity.SingleInstancePerTaskAffinityB
import com.example.lecture5.presentation.activities.single.instance.pertask.affinity.SingleInstancePerTaskAffinityC
import com.example.lecture5.presentation.activities.single.instance.pertask.affinity.SingleInstancePerTaskAffinityD
import com.example.lecture5.presentation.activities.single.task.SingleTaskA
import com.example.lecture5.presentation.activities.single.task.SingleTaskB
import com.example.lecture5.presentation.activities.single.task.SingleTaskC
import com.example.lecture5.presentation.activities.single.task.SingleTaskD
import com.example.lecture5.presentation.activities.single.task.affinity.SingleTaskAffinityA
import com.example.lecture5.presentation.activities.single.task.affinity.SingleTaskAffinityB
import com.example.lecture5.presentation.activities.single.task.affinity.SingleTaskAffinityC
import com.example.lecture5.presentation.activities.single.task.affinity.SingleTaskAffinityD
import com.example.lecture5.presentation.activities.single.top.SingleTopA
import com.example.lecture5.presentation.activities.single.top.SingleTopB
import com.example.lecture5.presentation.activities.single.top.SingleTopC
import com.example.lecture5.presentation.activities.single.top.SingleTopD
import com.example.lecture5.presentation.activities.single.top.affinity.SingleTopAffinityA
import com.example.lecture5.presentation.activities.single.top.affinity.SingleTopAffinityB
import com.example.lecture5.presentation.activities.single.top.affinity.SingleTopAffinityC
import com.example.lecture5.presentation.activities.single.top.affinity.SingleTopAffinityD
import com.example.lecture5.presentation.activities.standard.StandardA
import com.example.lecture5.presentation.activities.standard.StandardB
import com.example.lecture5.presentation.activities.standard.StandardC
import com.example.lecture5.presentation.activities.standard.StandardD
import com.example.lecture5.presentation.activities.standard.affinity.StandardAffinityA
import com.example.lecture5.presentation.activities.standard.affinity.StandardAffinityB
import com.example.lecture5.presentation.activities.standard.affinity.StandardAffinityC
import com.example.lecture5.presentation.activities.standard.affinity.StandardAffinityD

class ActivitiesProvider {

    val activities = listOf(
        ActivityStartInfo(
            "StandardActivity",
            StandardA::class.java,
            StandardB::class.java,
            StandardC::class.java,
            StandardD::class.java
        ),
        ActivityStartInfo(
            "StandardActivity with custom affinity",
            StandardAffinityA::class.java,
            StandardAffinityB::class.java,
            StandardAffinityC::class.java,
            StandardAffinityD::class.java
        ),
        ActivityStartInfo(
            "SingleTopActivity",
            SingleTopA::class.java,
            SingleTopB::class.java,
            SingleTopC::class.java,
            SingleTopD::class.java
        ),

        ActivityStartInfo(
            "SingleTopActivity with custom affinity",
            SingleTopAffinityA::class.java,
            SingleTopAffinityB::class.java,
            SingleTopAffinityC::class.java,
            SingleTopAffinityD::class.java
        ),


        ActivityStartInfo(
            "SingleTaskActivity",
            SingleTaskA::class.java,
            SingleTaskB::class.java,
            SingleTaskC::class.java,
            SingleTaskD::class.java
        ),

        ActivityStartInfo(
            "SingleTaskActivity with custom affinity",
            SingleTaskAffinityA::class.java,
            SingleTaskAffinityB::class.java,
            SingleTaskAffinityC::class.java,
            SingleTaskAffinityD::class.java
        ),

        ActivityStartInfo(
            "SingleInstanceActivity",
            SingleInstanceA::class.java,
            SingleInstanceB::class.java,
            SingleInstanceC::class.java,
            SingleInstanceD::class.java
        ),

        ActivityStartInfo(
            "SingleInstanceActivity with custom affinity",
            SingleInstanceAffinityA::class.java,
            SingleInstanceAffinityB::class.java,
            SingleInstanceAffinityC::class.java,
            SingleInstanceAffinityD::class.java
        ),

        ActivityStartInfo(
            "SingleInstancePerTaskActivity",
            SingleInstancePerTaskA::class.java,
            SingleInstancePerTaskB::class.java,
            SingleInstancePerTaskC::class.java,
            SingleInstancePerTaskD::class.java
        ),

        ActivityStartInfo(
            "SingleInstancePerTaskActivity with custom affinity",
            SingleInstancePerTaskAffinityA::class.java,
            SingleInstancePerTaskAffinityB::class.java,
            SingleInstancePerTaskAffinityC::class.java,
            SingleInstancePerTaskAffinityD::class.java
        ),

        )
}

