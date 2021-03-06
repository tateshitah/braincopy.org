<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
<html>
<head>
  <title>
    <xsl:value-of select="cook/menu/name" />
  </title>
</head>
<body text="gray" vlink="darkcyan" link="cadetblue" bgcolor="#ffffff"
 background="../glass.GIF">
<p align="right"><a href="cookmenu.html">お品書きへ</a></p>
<table cellspacing="10" cellpadding="0" border="0" bgcolor="white">
  <tbody>
    <tr>
      <td rowspan="2">
        <xsl:element name="img">
          <xsl:attribute name="alt"><xsl:value-of select="cook/menu/image/alt"/></xsl:attribute>
          <xsl:attribute name="src"><xsl:value-of select="cook/menu/image/file"/></xsl:attribute>
        </xsl:element>
      </td>
      <td bgcolor="firebrick">
      <p align="center"><span style="color: rgb(255, 255, 255);">
		<xsl:value-of select="cook/menu/name" />
	</span></p>
      </td>
    </tr>
    <tr>
      <td>
      <p>【材料】
        <xsl:value-of select="cook/menu/ingredients/amount" /></p>
      <ul>
        <xsl:for-each select="cook/menu/ingredients/ingredient">
          <li><xsl:value-of select="." /></li>
        </xsl:for-each>
      </ul>
      <p>【作り方】</p>
      <ol>
        <xsl:for-each select="cook/menu/how_to_cook/seq">
          <li><xsl:value-of select="." /></li>
        </xsl:for-each>
      </ol>
      <p>●　ポイント　●</p>
      <p><xsl:value-of select="cook/menu/point" /></p>
      </td>
    </tr>
  </tbody>
</table>
</body>
</html>
	</xsl:template>
</xsl:stylesheet>