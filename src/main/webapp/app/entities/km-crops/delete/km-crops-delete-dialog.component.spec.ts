jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { KmCropsService } from '../service/km-crops.service';

import { KmCropsDeleteDialogComponent } from './km-crops-delete-dialog.component';

describe('KmCrops Management Delete Component', () => {
  let comp: KmCropsDeleteDialogComponent;
  let fixture: ComponentFixture<KmCropsDeleteDialogComponent>;
  let service: KmCropsService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, KmCropsDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(KmCropsDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KmCropsDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(KmCropsService);
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
      }),
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
