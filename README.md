campo **linkNamePasth**
  
 Aqui você escreve o nome do seu usuário do GitHub, depois uma barra /, e o nome do repositório onde o arquivo vai ficar salvo.
 
Ex.:

Se seu usuário no GitHub for joao123 e o repositório for meu-projeto, então:

linkNamePasth = joao123/meu-projeto

------------------------------------------------------------------------------------------------

no campo **pasth**
   
   vai ser o local que direciona em qual pasta esta o arquivo, esses seram **obrigatorio** como *DownLoad e UpLoad*.

  **DownLoad e UpLoad**
  Name do file
  Ex.: pasth = pasta1/pasta2/arquivo.java(.go, .class, .dex, .lua etc.)

  Name do path
  Ex.: pasth = path1/path2

------------------------------------------------------------------------------------------------
  
campo **Commit**

  Aqui você coloca uma mensagem curta dizendo o que está enviando para o GitHub.
  
 Ex.:
 
Commit = Atualização do sistema de login

------------------------------------------------------------------------------------------------
  
 compo **toke**

Esse é o mais importante.
Aqui você coloca o código secreto do GitHub que dá permissão para enviar arquivos pro seu repositório. Esse código é chamado de “Token”.

Como pegar esse código (Token)

  1- Entre no site: https://github.com/settings/tokens

  2- Clique em “Generate new token (classic)”

  3- No campo "Note", escreva qualquer nome, por exemplo: "Token da minha engine"

  4- Em "Expiration", escolha “No expiration” (sem validade)

  5- Marque a opção repo (essa dá permissão para enviar arquivos)

  6- Clique em "Generate token"

  7- Copie o código que aparecer (vai começar com ghp_...)

  8- Cole esse código no campo toke no seu sistema.

Exemplo:
toke = ghp_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

   **link, para o tutorial?**
   
   Link:  https://youtu.be/T_97CyDFTEU?si=lcvCgngdHxwvuxIz
   
   
