-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 02, 2016 at 01:42 PM
-- Server version: 5.7.16-0ubuntu0.16.04.1
-- PHP Version: 7.0.8-0ubuntu0.16.04.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hendiDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `id` int(10) UNSIGNED NOT NULL,
  `article_id` int(10) DEFAULT NULL,
  `reply_to` int(10) DEFAULT '0',
  `user_id` int(10) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `content` text,
  `created` datetime DEFAULT NULL,
  `report` int(10) DEFAULT '0',
  `is_deleted` int(1) DEFAULT '0',
  `is_activated` int(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`id`, `article_id`, `reply_to`, `user_id`, `username`, `email`, `content`, `created`, `report`, `is_deleted`, `is_activated`) VALUES
(1, 411, 0, 212, 'hendisantika', 'hendisantika@yahoo.co.id', 'VXp1bWFraSBOYXJ1dG8gZGFuIFVjaGlhIFNhc3VrZSBrYWxpIGluaSBha2FuIGlrdXQgRGVtbyBC\nZWxhIElzbGFtIEppbGlkIDM=\n', '2016-11-30 07:50:35', 0, 0, 1),
(2, 411, 0, 212, 'hendisantika', 'hendisantika@yahoo.co.id', 'TWFudGFic3NzcyBkZWNoIFV6dW1ha2kgTmFydXRvIGFraGlybnlhIGJpc2EgbWVuamFkaSBIb2th\nZ2UgZGFuIGJlcnRlbWFuIGxhZ2kgZGVuZ2FuIHNhc3VrZQ==\n', '2016-11-30 18:55:11', 0, 1, 1),
(3, 411, 0, 212, 'hendisantika', 'hendisantika@yahoo.co.id', 'VXp1bWFraSBOYXJ1dG8gYWtoaXJueWEgYmlzYSBtZW5qYWRpIEhva2FnZSBkYW4gYmVydGVtYW4g\nbGFnaSBkZW5nYW4gc2FzdWtl\n', '2016-11-30 18:56:32', 0, 0, 1),
(4, 411, 0, 404, 'UzumakiNaruto', 'UzumakiNaruto@konohagakure', 'TWVuamFkaSBIb2thZ2UgYWRhbGFoIGphbGFuIG5pbmpha3U=\n', '2016-11-30 18:56:32', 0, 0, 1),
(5, 411, 0, 401, 'UchihaSasuke', 'UchihaSasuke@konohagakure', 'QWt1IGdhayBiaXNhIGphZGkgSG9rYWdlIGV1eQ==\n', '2016-11-30 18:56:32', 0, 0, 1),
(6, 411, 0, 401, 'NaaraShihakamaru', 'NaaraShihakamaru@konohagakure', 'QXBhbGFnaSBha3UgZ2FrIGJpc2EgamFkaSBIb2thZ2UgZG9uZw==\n', '2016-11-30 18:56:32', 0, 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `article_id` (`article_id`),
  ADD KEY `reply_to` (`reply_to`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
