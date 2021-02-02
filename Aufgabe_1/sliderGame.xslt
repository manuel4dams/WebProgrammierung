<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Verschiebespiel</title>
                <link rel="stylesheet" type="text/css" href="sliderGame.css"/>
            </head>
            <body>
                <main>
                    <header>
                        <xsl:value-of select="sliderGame/title"/>
                    </header>
                    <br/>
                    <table id="buttons">
                        <xsl:for-each select="sliderGame/table/row">
                            <tr>
                                <xsl:for-each select="box">
                                    <td>
                                        <xsl:value-of select="number"/>
                                    </td>
                                </xsl:for-each>
                            </tr>
                        </xsl:for-each>
                    </table>
                    <br/>
                    <table>
                        <tr>
                            <td>
                                <xsl:value-of select="sliderGame/counter/headerCount"/>
                            </td>
                            <td>
                                <b>
                                    <xsl:value-of select="sliderGame/counter/count"/>
                                </b>
                            </td>
                        </tr>
                    </table>
                    <br/>
                </main>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
