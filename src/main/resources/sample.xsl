<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema">
    <xsl:output method="text" />
    <xsl:template match="records">
        <xsl:value-of select="table[./name[text()='B']]/value" />
    </xsl:template>
</xsl:stylesheet>
