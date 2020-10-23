Université des Sciences et de la Technologie Houari Boumediene

![](RackMultipart20201023-4-whfs0e_html_e0b1e48d6880dd87.jpg)

# Technologie des agents

![](RackMultipart20201023-4-whfs0e_html_5d132339290f10de.gif)

# Rapport final du Projet

![](RackMultipart20201023-4-whfs0e_html_5d132339290f10de.gif)

Étudiants:

**SERIR Redouane (G3)**

**MAT  : 161631061908**

**Mail  :meritatan964@gmail.com**

**SAIGHI Mahmoud(G3)**

**MAT :161631091613**

**Mail  :Mahmoud.saighi.06@gmail.com**

**Introduction :**

La modernité dans la technologie est toujours associée à l&#39;efficacité́, la vitesse et la précision, et de réaliser tout cela nécessite plus d&#39;efforts, plus de coopération, et de participer aux travaux.

Les approches du commerce électronique classiques exigent une communication directe entre l&#39;acheteur et le vendeur. L&#39;acheteur va rechercher l&#39;information concernant le produit qu&#39;il désir, les étapes de recherche, de négociation, de paiement se font manuellement. Par conséquent les approches classiques :

-exigent un temps considérable de rassemblement de l&#39;information concernant les produits

-le choix de l&#39;acheteur est compromis par l&#39;augmentation des différents modes pour les vendeurs il devient de plus en plus difficile d&#39;attirer l&#39;attention des acheteurs

-le manque d&#39;information du coté acheteur comme du coté vendeur.

Pour combler tous ces défauts, Un système multi agents est l&#39;une des solutions les plus importantes de travail divisé, Ce qui forme aujourd&#39;hui un moyen pour développer et rendre des logiciels plus rapides et efficaces.

C&#39;est pour cela que dans ce projet nous allons utiliser les systèmes experts et les systèmes multi-agents, en concevant au début un système experts, puis un système multi-agents comportant des agents intelligents interagissant dans un environnement de vente et achat.

Ce travail sera composé de :

Partie 1 : Système expert
 partie 2 : Implémentation d&#39;un environnement de vente à base d&#39;agents intelligents
 partie 3 : Conclusion

**Partie 1 : Systèmes**  **expert**

**1.1- Définition d&#39;un système expert :**

Les systèmes experts sont une des applications de l&#39;IA. Ils imitent le raisonnement d&#39;un professionnel spécialiste dans un domaine précis par exemple en matière de diagnostic de panne automobile ou de diagnostic médical. Le système pose des questions à l&#39;utilisateur, ses réponses orientent le système qui au fur et à mesure affine son diagnostic.

Un système expert se compose de 3 parties :

- Une base de faits,
- Une base de règles et
- Un moteur d&#39;inférence

Et cela selon la figure suivante :

**B ![](RackMultipart20201023-4-whfs0e_html_bb0cd8df53c6e488.png)
 ase de connaissance :**

 Une base de connaissance est un ensemble de connaissance modélisée de telle sorte à être compréhensibles par un ordinateur, elle peut être sous la forme d&#39;une base de règle de productions, d&#39;une base de faits Ensemble de connaissances que le système considère comme vraies, ou des deux en même temps.

La Base de Faits (BF): est l&#39;une des entrées d&#39;un moteur d&#39;inférence. Elle contient les connaissances représentant des états considérés comme prouvé . C&#39;est la mémoire de travail du SE. Elle est variable au cours de l&#39;exécution et vidée lorsque l&#39;exécution est terminée. Les faits peuvent prendre des formes plus ou moins complexes.

La Base de Règles (BR) : La base de règles contient les connaissances expertes, c&#39;est- à-dire qu&#39;elles représentent les raisonnements effectués par un expert. Elles sont appelées les unes à la suite des autres afin de créer des enchaînements de raisonnements. Tous ces raisonnements peuvent être représentés sous la forme de règles de production du type « Si condition alors action ». Toutefois, cette représentation peut varier suivant le contexte de l&#39;application

**Moteur d&#39;inférence :**

Dans un système expert, un moteur d&#39;inférence est un module qui prend en entrée une base de connaissances, une base de faits et essaye, en appliquant soit l&#39;algorithme de chainage avant ou bien celui du chainage arrière ( tout dépendant du besoin ou la nature de la question posé par un utilisateur) de trouver un cheminement logique(i.e une succession d&#39;application de règle de production) de telle sorte à arriver une connaissance en particulier, ou bien à un ensemble de connaissances dérivable des faits introduit en entrée.

**Algorithme chainage avant :**

![](RackMultipart20201023-4-whfs0e_html_ca0f840720a92d3c.gif)

**Algorithme chainage arrière :**

![](RackMultipart20201023-4-whfs0e_html_3af399436b8dda61.gif)

**1.2- Réalisation**

Dans notre projet nous allons concevoir un système expert qui pour traiter différent domaine de vente, vente PC et véhicules

Il s&#39;agit de construire un système expert qui répond aux questions posées par l&#39;acheteur en se basant sur ses préférences afin de l&#39;aider à prendre une décision. Les préférences ici représentent la base de fait. Et pour cela nous avons utilisé le chainage avant dans le moteur d&#39;inférence.

Pour cela nous avons créé et utilisé plusieurs classes java parmi eux :

**Clause :** contient les données permettant de créer une clause (variables, condition...), la méthode permettant de l&#39;enregistrer, de vérifier sa véracité́...etc.

**Condition :** permet de traduire une condition définie en type String vers un code utilisable.

**Régle :** permet de définir une règle simple, et contient les méthodes nécessaire pour le processus d&#39;inférence.

**Basedeconnaisance :** définit un ensemble de RuleVariable et des Rules, ainsi que des méthodes permettant d&#39;implémenter le moteur d&#39;inférence.

**Controller :** tous les classes qui relient les interfaces au backend et d&#39;autres spécifications(JavaFX).

**Variable :** permet de définir une variable

**BasedeFaits :** définit un ensemble variable ainsi que des méthodes de gestion de requêtes.

D ![](RackMultipart20201023-4-whfs0e_html_d412599992d5a5b2.png)
 e plus nous avons Créer une base de données qui a pour schéma :

**1.3- Interfaces et fonctionnalité du SE :**

**1.3.1 Interface authentification :**

![](RackMultipart20201023-4-whfs0e_html_9992562b8c20df04.gif) ![](RackMultipart20201023-4-whfs0e_html_cd2f819a7de132d7.gif)

![](RackMultipart20201023-4-whfs0e_html_8d68385fb3f3265b.gif)

![](RackMultipart20201023-4-whfs0e_html_b261e41794123565.gif)

Dans cette partie un Client (Acheteur) ou Vendeur peut s&#39;identifier pour accéder aux fonctionnalités de l&#39;application ou bien s&#39;inscrire en créant un nouveau compte.

**Connexion**  : Pour se connecter, l&#39;utilisateur doit choisir son type, et entrer son nom d&#39;utilisateur et son mot de passe. S&#39;ils n&#39;existent pas alors l&#39;accès est refusé.

**1.3.2 Interface Client (Acheteur) :**

Après sa connexion le Client est dirigé vers l&#39;interface « acheteur ». Sur cette fenêtre, un acheteur peut effectuer une commande. Il choisit le domaine, le produit et les caractéristiques qu&#39;il veut (Marque, Ecrans…) et lancer une recherche pour avoir la meilleure proposition correspondant à ses choix.

![](RackMultipart20201023-4-whfs0e_html_50eacd6452880e54.gif)

4

**1.3.3 Interface Vendeur :**

Après sa connexion le vendeur est dirigé vers une interface vendeur ou il peut consulter les commandes des clients et ajouter des produits qu&#39;il possède comme le montre les figures suivantes

![](RackMultipart20201023-4-whfs0e_html_f223ec7f6a53069.gif)

![](RackMultipart20201023-4-whfs0e_html_d765565dd720cb7a.gif)

**Partie 2 : Implémentation d&#39;un environnement de vente à base d&#39;agents intelligents**

Sans cette partie il s&#39;agit d&#39;implémenter un environnement de vente comportant des acheteurs et des vendeurs. Les agents vendeurs négocient sur la vente d&#39;un produit choisi par l&#39;agent acheteur.

L&#39;agent acheteur demande aux agents vendeurs de lui faire des propositions pour l&#39;achat d&#39;un produit, et il définit pour cela certaines préférences sur les critères du produit. Les agents vendeurs font leurs propositions et la meilleure proposition répondant aux critères de l&#39;acheteur est lui est proposée.

**2.1- Principaux objectifs à réaliser dans l&#39;application :**

- Choix du produit à acheter par l&#39;acheteur.
- Définition des critères sur le produit choisi ainsi qu&#39;un prix maximal que l&#39;acheteur ne peut dépasser.
- Assurer la négociation des agents vendeurs.
- Définir le vendeur ou les vendeurs ayant fait les meilleures offres répondant aux préférences de l&#39;acheteur.
- Ne pas prendre en compte les propositions des vendeurs dont le prix de leur produit dépasse le prix maximal.
- Créer un profil pour l&#39;acheteur, afin de garder son historique d&#39;achat.
- Créer un profil pour chaque vendeur afin qu&#39;il puisse mettre à jour ses produits, ou ajouter de nouveau produit.
- Un nouveau vendeur ou un nouvel acheteur peut d&#39;inscrire et participer aux ventes.

**Définitions d&#39;agents :**

Un agent est un système informatique, situé dans un environnement, et qui agit d&#39;une façon autonome pour atteindre les objectifs dans un univers multi-agents, peut communiquer avec d&#39;autres agents, et dont le comportement est la conséquence de ses observations, de ses connaissances et des interactions avec les autres agents.

**Systèmes multi-agents :**

Un système multi-agents est un système distribué composé d&#39;un ensemble d&#39;agents. Un SMA est caractérisé ainsi :

- Chaque agent a des informations ou des capacités de résolution de problèmes (ainsi, chaque agent a un point de vue partiel).
- Il n&#39;y a aucun contrôle global du système multi-agents.
- Les données sont décentralisées.
- Le calcul est asynchrone.

**2.2- Architecture du système multi-agents :**

![](RackMultipart20201023-4-whfs0e_html_d886a92c18b59f9a.png)

Les agents communiquent par envoi de messages. Ils se comportent comme suit :

**1- Agent Acheteur :**

- Envoie aux vendeurs le produit choisit, le prix maximal, ainsi que les préférences sur chaque critères (on va prendre le prix, la qualité́ et les frais de livraison)

- Il attend les propositions des vendeurs

- Il choisit à l&#39;aide d&#39;une formule déterminée la meilleure proposition entre les propositions qui lui ont été́ envoyées

**2- Agent Vendeur :**

- Récupère le produit choisi par l&#39;acheteur ainsi que le prix maximal

- Il cherche dans la liste de ses produits le produit demandé

- S&#39;il ne possède pas se produit, il ne fait pas de proposition

- S&#39;il possède le produit mais que son prix et supérieur au prix maximal alors il ne fait pas de proposition

- Sinon il fait une proposition (le prix, qualité́, et frais de livraison du produit demandé)

- Il envoie à l&#39;agent acheteur sa proposition.

![](RackMultipart20201023-4-whfs0e_html_1d289712c5c4ac34.gif) Voici une figure qui montre l&#39;activation des agents :

**Partie 3 :** Conclusion

Dans la première partie nous avons développé un systèmes experts pour la vente commerciale , cependant leur manque d&#39;autonomie et de réaction avec le monde extérieur restait encore un obstacle majeur pour atteindre un niveau d&#39;intelligence semblable à celui de l&#39;homme, c&#39;est pour cela que nous avons utilisé les systèmes multi agents dans la deuxième partie, Cela a été fait en décomposant un traitement complexe en tâches simples affectées à des agents, ce qui a résulté en un comportement efficace. Ensuite une démonstration du système a été faite en utilisant une application qui l&#39;utilise.
