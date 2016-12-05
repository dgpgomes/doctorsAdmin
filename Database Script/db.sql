-- phpMyAdmin SQL Dump
-- version 4.2.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:8889
-- Generation Time: Jan 03, 2016 at 04:58 PM
-- Server version: 5.5.38
-- PHP Version: 5.6.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `gui_proj`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE `appointments` (
`id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `age` varchar(2) NOT NULL,
  `date` date NOT NULL,
  `address` varchar(120) NOT NULL,
  `contact_num` varchar(12) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`id`, `name`, `age`, `date`, `address`, `contact_num`) VALUES
(null, 'Douglas', '29', '2016-12-12', '1411 Van Apt b6', '0871671261'),
(null, 'Diogo', '29', '2016-12-01', '30 N Circular rd ', '0871520099'),
(null, 'Gustavo', '13', '2016-01-09', '43 Gloc Sqr', '0861526121');

-- --------------------------------------------------------

--
-- Table structure for table `guilogin`
--

CREATE TABLE `guilogin` (
`id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL DEFAULT '0',
  `password` varchar(50) NOT NULL DEFAULT '0',
  `type` varchar(50) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `guilogin`
--

INSERT INTO `guilogin` (`id`, `username`, `password`, `type`) VALUES
(null, 'kyle', '123', 'recp'),
(null, 'dgpgomes', 'banana09', 'doc'),
(null, 'euniceb', 'banana09', 'pharm');

-- --------------------------------------------------------

--
-- Table structure for table `prescription`
--

CREATE TABLE `prescription` (
`id` int(11) NOT NULL,
  `app_id` int(11) NOT NULL,
  `prescription` varchar(2000) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;


--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `guilogin`
--
ALTER TABLE `guilogin`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `prescription`
--
ALTER TABLE `prescription`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointments`
--
ALTER TABLE `appointments`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `guilogin`
--
ALTER TABLE `guilogin`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `prescription`
--
ALTER TABLE `prescription`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;