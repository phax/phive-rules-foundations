Brazilian electronic fiscal documents - upstream sources

Only XML Schemas exist. The business rules ("Regras de Validação") are only published as tables in the
"Manual de Orientação do Contribuinte" (MOC) and the "Notas Técnicas" (NT) and are checked by the tax authority.

The ZIP files in this folder are the unchanged originals. Only the files required by the registered root elements
were extracted to src/main/resources/external/schemas/<format>/<schema package>/

NF-e / NFC-e (model 55 / 65)
  Listing: https://www.nfe.fazenda.gov.br/portal/listaConteudo.aspx?tipoConteudo=BMPFMBoln3w=
  PL_010f_v1.04.zip - "Schemas XML NF-e - Pacote de Liberação nº 010f - NT 2025.002 v.1.50 e NT 2026.007 v.1.00 (Publicado em 31/08/26)"
    https://www.nfe.fazenda.gov.br/portal/exibirArquivo.aspx?conteudo=8ITFuBLltXs=
  PL_009p_NT2024_003_v1.03.zip - only used for "procNFe_v4.00.xsd" (root element "nfeProc"), which is not part of the 010 packages
    https://www.nfe.fazenda.gov.br/portal/exibirArquivo.aspx?conteudo=sjdK/JDjQs8=
  Note: the portal requires cookies (curl -c/-b cookie jar) and the download links are opaque tokens

CT-e (57), CT-e OS (67), GTV-e (64), CT-e Simplificado
  Listing: https://dfe-portal.svrs.rs.gov.br/Cte/Documentos
  PL_CTe_400_NT2026.002 RTC_1.01_corr_2.zip (21/08/2026)
    https://dfe-portal.svrs.rs.gov.br/CTE/DownloadArquivoEstatico/?sistema=CTE&tipoArquivo=2&nomeArquivo=PL_CTe_400_NT2026.002%20RTC_1.01_corr_2.zip

MDF-e (58)
  Listing: https://dfe-portal.svrs.rs.gov.br/Mdfe/Documentos
  PL_MDFe_300b_NT012025_1.04.zip (25/04/2026; inner folder is named "..._1.05")
    https://dfe-portal.svrs.rs.gov.br/MDFE/DownloadArquivoEstatico/?sistema=MDFE&tipoArquivo=2&nomeArquivo=PL_MDFe_300b_NT012025_1.04.zip

NFCom (62)
  Listing: https://dfe-portal.svrs.rs.gov.br/NFCOM/Documentos
  PL_NFCOM_1.00_NT2026.002 RTC_1.01.zip (29/06/2026)
    https://dfe-portal.svrs.rs.gov.br/NFCOM/DownloadArquivoEstatico/?sistema=NFCOM&tipoArquivo=2&nomeArquivo=PL_NFCOM_1.00_NT2026.002%20RTC_1.01.zip

NF3e (66)
  Listing: https://dfe-portal.svrs.rs.gov.br/NF3E/Documentos
  PL_NF3E_1.00a_NT2026.002 RTC_1.01.zip (29/06/2026)
    https://dfe-portal.svrs.rs.gov.br/NF3E/DownloadArquivoEstatico/?sistema=NF3E&tipoArquivo=2&nomeArquivo=PL_NF3E_1.00a_NT2026.002%20RTC_1.01.zip

BP-e (63), BP-e TM, BP-e TA
  Listing: https://dfe-portal.svrs.rs.gov.br/BPE/Documentos
  PL_BPe_100b_NT2026.002 RTC_1.01.zip (22/06/2026)
    https://dfe-portal.svrs.rs.gov.br/BPE/DownloadArquivoEstatico/?sistema=BPE&tipoArquivo=2&nomeArquivo=PL_BPe_100b_NT2026.002%20RTC_1.01.zip
  Note: there is no official "proc" schema for BP-e TA

NFAg (75)
  Listing: https://dfe-portal.svrs.rs.gov.br/NFAG/Documentos
  PL_NFAg_NT2026.002 RTC_1.01.zip (29/06/2026)
    https://dfe-portal.svrs.rs.gov.br/NFAG/DownloadArquivoEstatico/?sistema=NFAG&tipoArquivo=2&nomeArquivo=PL_NFAg_NT2026.002%20RTC_1.01.zip

NFGas (76)
  Listing: https://dfe-portal.svrs.rs.gov.br/NFGAS/Documentos
  PL_NFGas_NT2026.002 RTC_1.01.zip (29/06/2026)
    https://dfe-portal.svrs.rs.gov.br/NFGAS/DownloadArquivoEstatico/?sistema=NFGAS&tipoArquivo=2&nomeArquivo=PL_NFGas_NT2026.002%20RTC_1.01.zip

NFS-e Padrão Nacional
  Listing: https://www.gov.br/nfse/pt-br/biblioteca/documentacao-tecnica/producao-restrita
    (see also .../documentacao-tecnica/documentacao-atual and .../documentacao-tecnica/rtc)
  esquemas-nfse-rtc-v1-01-20260727.zip - "NFSe-ESQUEMAS_XSD-PRODREST-v1.01-20260727"
    https://www.gov.br/nfse/pt-br/biblioteca/documentacao-tecnica/producao-restrita/esquemas-nfse-rtc-v1-01-20260727.zip
  Note: the production package nfse-esquemas_xsd-v1-01-20260209.zip is NOT used, because its pattern for the DPS series
    ("^0{0,4}\d{1,5}$") treats "^" and "$" as literals in XSD and therefore rejects all real documents.

SVRS portals: a non-existing file name returns HTTP 200 with 0 bytes - check the size of the download.
