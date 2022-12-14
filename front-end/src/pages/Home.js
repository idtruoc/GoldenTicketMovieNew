import React, { Fragment } from 'react';
import Header from '../components/Header/Header';
import Footer from '../components/Footer/Footer';
import Hero from '../components/Hero/Hero';
import Movie from '../components/Movies/Movie';

const Home = () => {
  return (
    <Fragment>
      <Header />
        Homepage
        <Hero/>
        <Movie/>
      <Footer />
    </Fragment>
  )
}

export default Home