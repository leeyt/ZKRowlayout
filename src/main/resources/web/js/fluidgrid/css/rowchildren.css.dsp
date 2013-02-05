.z-rowlayout > .z-rowchildren[class*="colspan"] {
  display: block;
  float: left;

  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
}

@media (max-width: 767px) {
  .z-rowlayout > .z-rowchildren[class*="colspan"] {
    float: none;
  }
}
