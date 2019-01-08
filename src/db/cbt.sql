-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 08, 2019 at 04:54 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cbt`
--

-- --------------------------------------------------------

--
-- Table structure for table `answer`
--

CREATE TABLE `answer` (
  `id` int(11) NOT NULL,
  `course_registration_id` int(11) NOT NULL,
  `exam_question_id` int(11) NOT NULL,
  `text` text,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `assignment_answer`
--

CREATE TABLE `assignment_answer` (
  `id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `file` blob NOT NULL,
  `type` varchar(4) NOT NULL,
  `question_id` int(11) NOT NULL,
  `student_id` varchar(20) NOT NULL,
  `score` int(3) NOT NULL DEFAULT '0',
  `marked` enum('true','false','','') NOT NULL DEFAULT 'false'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `assignment_answer`
--

INSERT INTO `assignment_answer` (`id`, `teacher_id`, `name`, `file`, `type`, `question_id`, `student_id`, `score`, `marked`) VALUES
(1, 3, 'Aduchie Oluoma Precious', 0x5768617420697320746865206e616d65206f662074686520707265736964656e74206f6620746865206665646572616c2072657075626c6963206f66206e6967657269613f0d0a4962726168696d20626162616e676964610d0a6162647573616c616d2061627562616b61720d0a4d7568616d6d616475206275686172690d0a0d0a49662061203d203120616e642062203d32207768617420697320633f0d0a330d0a340d0a350d0a360d0a0d0a496620796f7520617265206e6f74207374757069642c20776861742061726520796f753f0d0a696e74656c6c6967656e740d0a706f77657266756c0d0a726963680d0a706f6f720d0a0d0a0d0a5768617420697320746865206e616d65206f66207468652063757272656e637920696e204768616e613f0d0a6b756d6173690d0a616b776162610d0a636564690d0a6b6967616c690d0a0d0a0d0a, 'txt', 1, 'a', 67, 'true');

-- --------------------------------------------------------

--
-- Table structure for table `assignment_question`
--

CREATE TABLE `assignment_question` (
  `id` int(11) NOT NULL,
  `teacher_id` int(5) NOT NULL,
  `question` text NOT NULL,
  `picture` longblob,
  `type` varchar(3) DEFAULT NULL,
  `deadline` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `assignment_question`
--

INSERT INTO `assignment_question` (`id`, `teacher_id`, `question`, `picture`, `type`, `deadline`) VALUES
(1, 3, 'What is the name of the president of the federal republic of nigeria?', NULL, NULL, '2011-09-00 00:00:00'),
(2, 3, 'If a = 1 and b =2 what is c?', NULL, NULL, '2011-09-00 00:00:00'),
(3, 3, 'If you are not stupid, what are you?', NULL, NULL, '2011-09-00 00:00:00'),
(4, 3, 'What is the name of the currency in Ghana?', NULL, NULL, '2011-09-00 00:00:00'),
(5, 3, 'What is the name of the president of the federal republic of nigeria?', NULL, NULL, '2011-09-00 00:00:00'),
(6, 3, 'If a = 1 and b =2 what is c?\r', NULL, NULL, '2011-09-00 00:00:00'),
(7, 3, 'If you are not stupid, what are you?\r', NULL, NULL, '2011-09-00 00:00:00'),
(8, 3, 'What is the name of the currency in Ghana?\r', NULL, NULL, '2011-09-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id` int(11) NOT NULL,
  `course_code` varchar(10) NOT NULL,
  `course_title` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`id`, `course_code`, `course_title`) VALUES
(1, 'CSC 101', 'Introduction to Computer Science'),
(2, 'CSC 103', 'System Architecture'),
(3, 'CSC 235', 'System Architecture');

-- --------------------------------------------------------

--
-- Table structure for table `course_registration`
--

CREATE TABLE `course_registration` (
  `id` int(11) NOT NULL,
  `reg_number` varchar(20) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `result` int(3) NOT NULL,
  `status` enum('open','close','','') NOT NULL DEFAULT 'close'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course_registration`
--

INSERT INTO `course_registration` (`id`, `reg_number`, `teacher_id`, `result`, `status`) VALUES
(1, 'a', 3, 0, 'close');

-- --------------------------------------------------------

--
-- Table structure for table `document`
--

CREATE TABLE `document` (
  `id` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `file` blob NOT NULL,
  `type` text NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `document`
--

INSERT INTO `document` (`id`, `teacher_id`, `name`, `file`, `type`, `date_created`) VALUES
(1, 1, 'questionaire.txt', 0x5768617420697320746865206e616d65206f662074686520707265736964656e74206f6620746865206665646572616c2072657075626c6963206f66206e6967657269613f0d0a4962726168696d20626162616e676964610d0a6162647573616c616d2061627562616b61720d0a4d7568616d6d616475206275686172690d0a0d0a49662061203d203120616e642062203d32207768617420697320633f0d0a330d0a340d0a350d0a360d0a0d0a496620796f7520617265206e6f74207374757069642c20776861742061726520796f753f0d0a696e74656c6c6967656e740d0a706f77657266756c0d0a726963680d0a706f6f720d0a0d0a0d0a5768617420697320746865206e616d65206f66207468652063757272656e637920696e204768616e613f0d0a6b756d6173690d0a616b776162610d0a636564690d0a6b6967616c690d0a0d0a0d0a, 'txt', '2019-01-05 16:45:52');

-- --------------------------------------------------------

--
-- Table structure for table `exam_question`
--

CREATE TABLE `exam_question` (
  `id` int(11) NOT NULL,
  `teacher_id` int(5) NOT NULL,
  `question` text NOT NULL,
  `a` varchar(80) DEFAULT NULL,
  `b` varchar(80) DEFAULT NULL,
  `c` varchar(80) DEFAULT NULL,
  `d` varchar(80) DEFAULT NULL,
  `e` varchar(80) DEFAULT NULL,
  `picture` longblob,
  `type` varchar(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `id` varchar(20) NOT NULL,
  `first_name` varchar(40) NOT NULL,
  `access_level` enum('0','1') NOT NULL DEFAULT '0',
  `last_name` varchar(40) NOT NULL,
  `middle_name` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `first_name`, `access_level`, `last_name`, `middle_name`, `password`) VALUES
('2008287482', 'Camelo', '0', 'Ferdinand', 'Anthony', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8'),
('2008287484', 'Amanda', '0', 'Faith', 'Akpan', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8'),
('2008287485', 'Israel', '0', 'Adebayo', 'Lawal', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8'),
('a', 'Oluoma', '0', 'Aduchie', 'Precious', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8'),
('b', 'amaobi', '1', 'Obikobe', 'Victor', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8');

-- --------------------------------------------------------

--
-- Table structure for table `session`
--

CREATE TABLE `session` (
  `id` int(5) NOT NULL,
  `name` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `session`
--

INSERT INTO `session` (`id`, `name`) VALUES
(1, '2018/2019');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `person_id` varchar(20) NOT NULL,
  `department` varchar(40) NOT NULL,
  `level` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`person_id`, `department`, `level`) VALUES
('2008287482', 'Computer Science', 100),
('2008287484', 'Computer Science', 100),
('2008287485', 'Computer Science', 100),
('a', 'Computer Science', 100);

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `id` int(5) NOT NULL,
  `person_id` varchar(20) NOT NULL,
  `course_id` int(5) NOT NULL,
  `session_id` int(5) NOT NULL,
  `duration` time NOT NULL DEFAULT '00:01:00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`id`, `person_id`, `course_id`, `session_id`, `duration`) VALUES
(3, 'b', 1, 1, '11:09:00'),
(4, 'b', 2, 1, '00:01:00'),
(5, 'b', 3, 1, '00:01:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answer`
--
ALTER TABLE `answer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `assignment_answer`
--
ALTER TABLE `assignment_answer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `assignment_question`
--
ALTER TABLE `assignment_question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`teacher_id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `course_registration`
--
ALTER TABLE `course_registration`
  ADD PRIMARY KEY (`id`),
  ADD KEY `CR_reg_number` (`reg_number`),
  ADD KEY `CR_teacher_id` (`teacher_id`);

--
-- Indexes for table `document`
--
ALTER TABLE `document`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exam_question`
--
ALTER TABLE `exam_question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`teacher_id`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`first_name`,`last_name`);

--
-- Indexes for table `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`person_id`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_set` (`person_id`,`course_id`,`session_id`),
  ADD KEY `t_course_id` (`course_id`),
  ADD KEY `t_session_id` (`session_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `answer`
--
ALTER TABLE `answer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `assignment_answer`
--
ALTER TABLE `assignment_answer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `assignment_question`
--
ALTER TABLE `assignment_question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `course_registration`
--
ALTER TABLE `course_registration`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `document`
--
ALTER TABLE `document`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `exam_question`
--
ALTER TABLE `exam_question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=185;

--
-- AUTO_INCREMENT for table `session`
--
ALTER TABLE `session`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `teacher`
--
ALTER TABLE `teacher`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `course_registration`
--
ALTER TABLE `course_registration`
  ADD CONSTRAINT `CR_reg_number` FOREIGN KEY (`reg_number`) REFERENCES `person` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `CR_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `exam_question`
--
ALTER TABLE `exam_question`
  ADD CONSTRAINT `exam_question_reg` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `reg_number` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `teacher`
--
ALTER TABLE `teacher`
  ADD CONSTRAINT `t_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `t_person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `t_session_id` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
