import Vue from "vue";

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
  faHeart as fasHeart,
  faMagnifyingGlass as faMagnifyingGlass,
  faCirclePlus,
  faUserPlus,
  faFileCirclePlus,
  faTrash,
} from "@fortawesome/free-solid-svg-icons";
import {
  faHeart as farHeart,
  faCircleCheck as faCircleCheck,
  faPenToSquare,
} from "@fortawesome/free-regular-svg-icons";

library.add(
  fasHeart,
  farHeart,
  faMagnifyingGlass,
  faCircleCheck,
  faCirclePlus,
  faUserPlus,
  faFileCirclePlus,
  faPenToSquare,
  faTrash
);

Vue.component("font-awesome-icon", FontAwesomeIcon);
