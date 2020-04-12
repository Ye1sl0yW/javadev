-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  mer. 25 mars 2020 à 17:38
-- Version du serveur :  10.4.8-MariaDB
-- Version de PHP :  7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `shoppy`
--

-- --------------------------------------------------------

--
-- Structure de la table `adresse`
--

CREATE TABLE `adresse` (
  `id` int(11) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `zip` int(11) NOT NULL,
  `rue` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `numero` int(11) NOT NULL,
  `pays` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ville` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id` int(11) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `QteTot` int(11) NOT NULL,
  `DateCreation` datetime NOT NULL,
  `adresse_liv` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `id_Acheteur` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `ligne_cmd`
--

CREATE TABLE `ligne_cmd` (
  `id` int(11) NOT NULL,
  `id_cmd` int(11) DEFAULT NULL,
  `id_product` int(11) DEFAULT NULL,
  `qte` int(11) NOT NULL,
  `totalLigne` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `magasin`
--

CREATE TABLE `magasin` (
  `id` int(11) NOT NULL,
  `id_vendeur` int(11) DEFAULT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `taille_stock` int(11) DEFAULT NULL,
  `matricule_fiscal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `magasin`
--

INSERT INTO `magasin` (`id`, `id_vendeur`, `nom`, `taille_stock`, `matricule_fiscal`) VALUES
(1, 1, 'qsdfghj', 20, 20),
(2, 3, 'sdfgh', 20, 20);

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `thread_id` int(11) DEFAULT NULL,
  `sender_id` int(11) DEFAULT NULL,
  `body` longtext COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `message`
--

INSERT INTO `message` (`id`, `thread_id`, `sender_id`, `body`, `created_at`) VALUES
(1, 1, 2, 'tnyntn', '2020-02-26 23:28:25'),
(2, 2, 3, 'zertyuiop', '2020-02-27 10:44:10');

-- --------------------------------------------------------

--
-- Structure de la table `message_metadata`
--

CREATE TABLE `message_metadata` (
  `id` int(11) NOT NULL,
  `message_id` int(11) DEFAULT NULL,
  `participant_id` int(11) DEFAULT NULL,
  `is_read` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `message_metadata`
--

INSERT INTO `message_metadata` (`id`, `message_id`, `participant_id`, `is_read`) VALUES
(1, 1, 1, 0),
(2, 1, 2, 1),
(3, 2, 2, 0),
(4, 2, 3, 1);

-- --------------------------------------------------------

--
-- Structure de la table `notes`
--

CREATE TABLE `notes` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `produit_id` int(11) DEFAULT NULL,
  `magasin_id` int(11) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  `text` longtext COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `notes`
--

INSERT INTO `notes` (`id`, `user_id`, `produit_id`, `magasin_id`, `type`, `value`, `text`) VALUES
(1, 2, 1, NULL, 0, 7, 'zert'),
(2, NULL, NULL, 1, 1, 10, 'dfghj'),
(3, 3, NULL, 2, 1, 10, 'esdfgh');

-- --------------------------------------------------------

--
-- Structure de la table `offre`
--

CREATE TABLE `offre` (
  `id` int(11) NOT NULL,
  `id_magasin` int(11) DEFAULT NULL,
  `taux` double NOT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'AJOUTER NOM',
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'AJOUTER DESCRIPTION',
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

CREATE TABLE `panier` (
  `id` int(11) NOT NULL,
  `qte` int(11) NOT NULL,
  `totCart` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `portfolios`
--

CREATE TABLE `portfolios` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `portfolios`
--

INSERT INTO `portfolios` (`id`, `user_id`) VALUES
(1, NULL),
(2, 1),
(4, 2),
(5, 3),
(6, 4),
(7, 5);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `id_magasin` int(11) DEFAULT NULL,
  `quantite` int(11) NOT NULL,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `prix` double NOT NULL,
  `marque` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `image_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `id_magasin`, `quantite`, `nom`, `description`, `prix`, `marque`, `image_name`, `updated_at`) VALUES
(1, NULL, 1, 'QSDFGHJKL', 'qzsedrftghjk', 10, 'szedrfghjkl', 'ert', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `produit_categorie`
--

CREATE TABLE `produit_categorie` (
  `produit_id` int(11) NOT NULL,
  `categorie_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `rec`
--

CREATE TABLE `rec` (
  `id` int(11) NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nomClient` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `contenu` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `rep`
--

CREATE TABLE `rep` (
  `id` int(11) NOT NULL,
  `rec` int(11) DEFAULT NULL,
  `nomResponsable` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `repo` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `dateRep` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `thread`
--

CREATE TABLE `thread` (
  `id` int(11) NOT NULL,
  `created_by_id` int(11) DEFAULT NULL,
  `subject` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `is_spam` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `thread`
--

INSERT INTO `thread` (`id`, `created_by_id`, `subject`, `created_at`, `is_spam`) VALUES
(1, 2, 'tgbtrt', '2020-02-26 23:28:25', 0),
(2, 3, 'ertyui', '2020-02-27 10:44:10', 0);

-- --------------------------------------------------------

--
-- Structure de la table `thread_metadata`
--

CREATE TABLE `thread_metadata` (
  `id` int(11) NOT NULL,
  `thread_id` int(11) DEFAULT NULL,
  `participant_id` int(11) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `last_participant_message_date` datetime DEFAULT NULL,
  `last_message_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `thread_metadata`
--

INSERT INTO `thread_metadata` (`id`, `thread_id`, `participant_id`, `is_deleted`, `last_participant_message_date`, `last_message_date`) VALUES
(1, 1, 1, 0, NULL, '2020-02-26 23:28:25'),
(2, 1, 2, 1, '2020-02-26 23:28:25', NULL),
(3, 2, 2, 0, NULL, '2020-02-27 10:44:10'),
(4, 2, 3, 0, '2020-02-27 10:44:10', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `tickets`
--

CREATE TABLE `tickets` (
  `id` int(11) NOT NULL,
  `portfolio_id` int(11) DEFAULT NULL,
  `montant` int(11) NOT NULL,
  `date_exp` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `tickets`
--

INSERT INTO `tickets` (`id`, `portfolio_id`, `montant`, `date_exp`) VALUES
(1, 1, 2, '2015-01-01'),
(3, 1, 200, '2020-03-24');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `portfolio_id` int(11) DEFAULT NULL,
  `id_magasin` int(11) DEFAULT NULL,
  `username` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `username_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `confirmation_token` varchar(180) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_requested_at` datetime DEFAULT NULL,
  `roles` longtext COLLATE utf8_unicode_ci NOT NULL COMMENT '(DC2Type:array)',
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `prenom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `sexe` tinyint(1) NOT NULL,
  `tel` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `portfolio_id`, `id_magasin`, `username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `confirmation_token`, `password_requested_at`, `roles`, `nom`, `prenom`, `sexe`, `tel`) VALUES
(1, 1, NULL, 'aaaaa', 'aaaaa', 'ytjdtthrt@the.rgr', 'ytjdtthrt@the.rgr', 1, NULL, '$2y$13$RGITVniQSOuYSMNE9Nukpu/HlRdTLoEFOjL.tv.tvGSLpFgIQ2Uwy', '2020-02-26 18:08:30', NULL, NULL, 'a:1:{i:0;s:13:\"ROLE_ACHETEUR\";}', 'aaaaa', 'aaaaa', 1, '2583'),
(2, 4, NULL, 'bbbbb', 'bbbbb', 'zqesdgfhj@sedtfyg.oiujyhgfd', 'zqesdgfhj@sedtfyg.oiujyhgfd', 1, NULL, '$2y$13$K/D7BMpGubm00vIYRckZkOZCgg/LBfXlVfz1zIWqNx6MjArqBFsIm', '2020-02-27 10:45:16', NULL, NULL, 'a:1:{i:0;s:13:\"ROLE_ACHETEUR\";}', 'bbbbb', 'jytku', 1, '8524'),
(3, 5, NULL, 'aaaaaa', 'aaaaaa', 'azertgyh@kjhgfd.hgfd', 'azertgyh@kjhgfd.hgfd', 1, NULL, '$2y$13$EDzOUUaKCGe9scwXhvLdz.xTUvxUYscfNyaOsjPUc1eaKNRwGVN6a', '2020-02-27 01:42:22', NULL, NULL, 'a:1:{i:0;s:13:\"ROLE_ACHETEUR\";}', 'aaaaa', 'aaaaa', 1, '85'),
(4, 6, NULL, 'avav', 'avav', 'zsefrgtyuik@zefrgthyjdfgh.zerfgh', 'zsefrgtyuik@zefrgthyjdfgh.zerfgh', 1, NULL, '$2y$13$wteTva9aPv1yHE27FPXYNuz7WVDtwkACWw3mVgBh9AVhFMjZXg80O', '2020-02-27 10:49:31', NULL, NULL, 'a:1:{i:0;s:13:\"ROLE_ACHETEUR\";}', 'avava', 'avava', 1, '8752'),
(5, 7, NULL, 'avavaav', 'avavaav', 'zsefrgtyuik@zefrgh.zerfgh', 'zsefrgtyuik@zefrgh.zerfgh', 1, NULL, '$2y$13$DM7KaOE/LsCyGdc1hOIajOAJF.cS5MC3QJDF8nwsrTO295yno3Z5C', '2020-02-27 10:52:10', NULL, NULL, 'a:1:{i:0;s:13:\"ROLE_ACHETEUR\";}', 'avava', 'avava', 1, '21655508084');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `adresse`
--
ALTER TABLE `adresse`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_C35F08166B3CA4B` (`id_user`);

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_6EEAA67D99E7715E` (`id_Acheteur`);

--
-- Index pour la table `ligne_cmd`
--
ALTER TABLE `ligne_cmd`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_FDB4E3D4B1F8F49A` (`id_cmd`),
  ADD KEY `IDX_FDB4E3D4DD7ADDD` (`id_product`);

--
-- Index pour la table `magasin`
--
ALTER TABLE `magasin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_54AF5F27A46930E6` (`id_vendeur`);

--
-- Index pour la table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_B6BD307FE2904019` (`thread_id`),
  ADD KEY `IDX_B6BD307FF624B39D` (`sender_id`);

--
-- Index pour la table `message_metadata`
--
ALTER TABLE `message_metadata`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_4632F005537A1329` (`message_id`),
  ADD KEY `IDX_4632F0059D1C3019` (`participant_id`);

--
-- Index pour la table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_11BA68CA76ED395` (`user_id`),
  ADD KEY `IDX_11BA68CF347EFB` (`produit_id`),
  ADD KEY `IDX_11BA68C20096AE3` (`magasin_id`);

--
-- Index pour la table `offre`
--
ALTER TABLE `offre`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_AF86866F8A32F657` (`id_magasin`);

--
-- Index pour la table `panier`
--
ALTER TABLE `panier`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `portfolios`
--
ALTER TABLE `portfolios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_B81B226FA76ED395` (`user_id`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_29A5EC278A32F657` (`id_magasin`);

--
-- Index pour la table `produit_categorie`
--
ALTER TABLE `produit_categorie`
  ADD PRIMARY KEY (`produit_id`,`categorie_id`),
  ADD KEY `IDX_CDEA88D8F347EFB` (`produit_id`),
  ADD KEY `IDX_CDEA88D8BCF5E72D` (`categorie_id`);

--
-- Index pour la table `rec`
--
ALTER TABLE `rec`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `rep`
--
ALTER TABLE `rep`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_E0BB8BF26405CA2C` (`rec`);

--
-- Index pour la table `thread`
--
ALTER TABLE `thread`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_31204C83B03A8386` (`created_by_id`);

--
-- Index pour la table `thread_metadata`
--
ALTER TABLE `thread_metadata`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_40A577C8E2904019` (`thread_id`),
  ADD KEY `IDX_40A577C89D1C3019` (`participant_id`);

--
-- Index pour la table `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_54469DF4B96B5643` (`portfolio_id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_1483A5E992FC23A8` (`username_canonical`),
  ADD UNIQUE KEY `UNIQ_1483A5E9A0D96FBF` (`email_canonical`),
  ADD UNIQUE KEY `UNIQ_1483A5E9C05FB297` (`confirmation_token`),
  ADD UNIQUE KEY `UNIQ_1483A5E9B96B5643` (`portfolio_id`),
  ADD UNIQUE KEY `UNIQ_1483A5E98A32F657` (`id_magasin`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `adresse`
--
ALTER TABLE `adresse`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `ligne_cmd`
--
ALTER TABLE `ligne_cmd`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `magasin`
--
ALTER TABLE `magasin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `message_metadata`
--
ALTER TABLE `message_metadata`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `notes`
--
ALTER TABLE `notes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `offre`
--
ALTER TABLE `offre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `panier`
--
ALTER TABLE `panier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `portfolios`
--
ALTER TABLE `portfolios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `rec`
--
ALTER TABLE `rec`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `rep`
--
ALTER TABLE `rep`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `thread`
--
ALTER TABLE `thread`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `thread_metadata`
--
ALTER TABLE `thread_metadata`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `tickets`
--
ALTER TABLE `tickets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `adresse`
--
ALTER TABLE `adresse`
  ADD CONSTRAINT `FK_C35F08166B3CA4B` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `FK_6EEAA67D99E7715E` FOREIGN KEY (`id_Acheteur`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `ligne_cmd`
--
ALTER TABLE `ligne_cmd`
  ADD CONSTRAINT `FK_FDB4E3D4B1F8F49A` FOREIGN KEY (`id_cmd`) REFERENCES `commande` (`id`),
  ADD CONSTRAINT `FK_FDB4E3D4DD7ADDD` FOREIGN KEY (`id_product`) REFERENCES `produit` (`id`);

--
-- Contraintes pour la table `magasin`
--
ALTER TABLE `magasin`
  ADD CONSTRAINT `FK_54AF5F27A46930E6` FOREIGN KEY (`id_vendeur`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `FK_B6BD307FE2904019` FOREIGN KEY (`thread_id`) REFERENCES `thread` (`id`),
  ADD CONSTRAINT `FK_B6BD307FF624B39D` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `message_metadata`
--
ALTER TABLE `message_metadata`
  ADD CONSTRAINT `FK_4632F005537A1329` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  ADD CONSTRAINT `FK_4632F0059D1C3019` FOREIGN KEY (`participant_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `FK_11BA68C20096AE3` FOREIGN KEY (`magasin_id`) REFERENCES `magasin` (`id`),
  ADD CONSTRAINT `FK_11BA68CA76ED395` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK_11BA68CF347EFB` FOREIGN KEY (`produit_id`) REFERENCES `produit` (`id`);

--
-- Contraintes pour la table `offre`
--
ALTER TABLE `offre`
  ADD CONSTRAINT `FK_AF86866F8A32F657` FOREIGN KEY (`id_magasin`) REFERENCES `magasin` (`id`);

--
-- Contraintes pour la table `portfolios`
--
ALTER TABLE `portfolios`
  ADD CONSTRAINT `FK_B81B226FA76ED395` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `FK_29A5EC278A32F657` FOREIGN KEY (`id_magasin`) REFERENCES `magasin` (`id`);

--
-- Contraintes pour la table `produit_categorie`
--
ALTER TABLE `produit_categorie`
  ADD CONSTRAINT `FK_CDEA88D8BCF5E72D` FOREIGN KEY (`categorie_id`) REFERENCES `categorie` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_CDEA88D8F347EFB` FOREIGN KEY (`produit_id`) REFERENCES `produit` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `rep`
--
ALTER TABLE `rep`
  ADD CONSTRAINT `FK_E0BB8BF26405CA2C` FOREIGN KEY (`rec`) REFERENCES `rec` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `thread`
--
ALTER TABLE `thread`
  ADD CONSTRAINT `FK_31204C83B03A8386` FOREIGN KEY (`created_by_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `thread_metadata`
--
ALTER TABLE `thread_metadata`
  ADD CONSTRAINT `FK_40A577C89D1C3019` FOREIGN KEY (`participant_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK_40A577C8E2904019` FOREIGN KEY (`thread_id`) REFERENCES `thread` (`id`);

--
-- Contraintes pour la table `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `FK_54469DF4B96B5643` FOREIGN KEY (`portfolio_id`) REFERENCES `portfolios` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FK_1483A5E98A32F657` FOREIGN KEY (`id_magasin`) REFERENCES `magasin` (`id`),
  ADD CONSTRAINT `FK_1483A5E9B96B5643` FOREIGN KEY (`portfolio_id`) REFERENCES `portfolios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
