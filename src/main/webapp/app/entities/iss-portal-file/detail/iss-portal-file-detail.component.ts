import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssPortalFile } from '../iss-portal-file.model';

@Component({
  selector: 'jhi-iss-portal-file-detail',
  templateUrl: './iss-portal-file-detail.component.html',
})
export class IssPortalFileDetailComponent implements OnInit {
  issPortalFile: IIssPortalFile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issPortalFile }) => {
      this.issPortalFile = issPortalFile;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
