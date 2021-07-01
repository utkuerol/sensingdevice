{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "sensingdevice.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "sensingdevice.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "sensingdevice.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Ceate name for ConfigMap
*/}}
{{- define "sensingdevice.configmapfile" -}}
{{- printf "%s-%s-%s" "proxy-file" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- define "sensingdevice.configmap" -}}
{{- printf "%s-%s-%s" "proxy" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Common labels
*/}}
{{- define "sensingdevice.labels" -}}
helm.sh/chart: {{ include "sensingdevice.chart" . }}
{{ include "sensingdevice.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end -}}

{{/*
Selector labels
*/}}
{{- define "sensingdevice.selectorLabels" -}}
app.kubernetes.io/name: {{ include "sensingdevice.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end -}}

{{/*
Create the name of the service account to use
*/}}
{{- define "sensingdevice.serviceAccountName" -}}
{{- if .Values.serviceAccount.create -}}
    {{ default (include "sensingdevice.fullname" .) .Values.serviceAccount.name }}
{{- else -}}
    {{ default "default" .Values.serviceAccount.name }}
{{- end -}}
{{- end -}}

{{/*
Create the name of the postgresql service
*/}}
{{ define "postgresql.serviceName" }}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name "postgresql" | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create the name of the postgresql secret

if not existingSecret was passed to postgresql, use default secret name (created by postgresql chart)

TODO check subchart postgresql's template "postgresq.secretName", if its fine, include the subcharts template instead 
*/}}
{{ define "postgresql.secretName" }}
{{- if .Values.postgresql -}}
    {{- if .Values.postgresql.existingSecret -}}
    {{- .Values.postgresql.existingSecret | trunc 63 | trimSuffix "-" -}}
    {{- else -}}
    {{- printf "%s-%s" .Release.Name "postgresql" | trunc 63 | trimSuffix "-" -}}
    {{- end -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name "postgresql" | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}


{{/*
Create the name of the mongodb secret
    
if not existingSecret was passed to mongodb, use default secret name (created by mongodb chart)
*/}}
{{ define "mongodb.secretName" }}
{{- if .Values.mongodb -}}
    {{- if .Values.mongodb.auth.existingSecret -}}
    {{- .Values.mongodb.auth.existingSecret | trunc 63 | trimSuffix "-" -}}
    {{- else -}}
    {{- printf "%s-%s" .Release.Name "mongodb" | trunc 63 | trimSuffix "-" -}}
    {{- end -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name "mongodb" | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
    
    


{{/*
Create the name of the mongodb service
*/}}
{{ define "mongodb.serviceName" }}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name "mongodb" | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}
