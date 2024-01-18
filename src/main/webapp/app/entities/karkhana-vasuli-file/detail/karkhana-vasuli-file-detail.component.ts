import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKarkhanaVasuliFile } from '../karkhana-vasuli-file.model';

@Component({
  selector: 'jhi-karkhana-vasuli-file-detail',
  templateUrl: './karkhana-vasuli-file-detail.component.html',
})
export class KarkhanaVasuliFileDetailComponent implements OnInit {
  karkhanaVasuliFile: IKarkhanaVasuliFile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ karkhanaVasuliFile }) => {
      this.karkhanaVasuliFile = karkhanaVasuliFile;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
