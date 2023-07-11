jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { LandTypeMasterService } from '../service/land-type-master.service';

import { LandTypeMasterDeleteDialogComponent } from './land-type-master-delete-dialog.component';

describe('LandTypeMaster Management Delete Component', () => {
  let comp: LandTypeMasterDeleteDialogComponent;
  let fixture: ComponentFixture<LandTypeMasterDeleteDialogComponent>;
  let service: LandTypeMasterService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LandTypeMasterDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(LandTypeMasterDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LandTypeMasterDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LandTypeMasterService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
