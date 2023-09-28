import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKMPUpload } from '../kmp-upload.model';

@Component({
  selector: 'jhi-kmp-upload-detail',
  templateUrl: './kmp-upload-detail.component.html',
})
export class KMPUploadDetailComponent implements OnInit {
  kMPUpload: IKMPUpload | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kMPUpload }) => {
      this.kMPUpload = kMPUpload;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
