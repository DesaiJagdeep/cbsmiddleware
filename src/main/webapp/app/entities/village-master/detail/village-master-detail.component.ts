import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVillageMaster } from '../village-master.model';

@Component({
  selector: 'jhi-village-master-detail',
  templateUrl: './village-master-detail.component.html',
})
export class VillageMasterDetailComponent implements OnInit {
  villageMaster: IVillageMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ villageMaster }) => {
      this.villageMaster = villageMaster;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
