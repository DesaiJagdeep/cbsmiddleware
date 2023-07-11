import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICropCategoryMaster, NewCropCategoryMaster } from '../crop-category-master.model';

export type PartialUpdateCropCategoryMaster = Partial<ICropCategoryMaster> & Pick<ICropCategoryMaster, 'id'>;

export type EntityResponseType = HttpResponse<ICropCategoryMaster>;
export type EntityArrayResponseType = HttpResponse<ICropCategoryMaster[]>;

@Injectable({ providedIn: 'root' })
export class CropCategoryMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/crop-category-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cropCategoryMaster: NewCropCategoryMaster): Observable<EntityResponseType> {
    return this.http.post<ICropCategoryMaster>(this.resourceUrl, cropCategoryMaster, { observe: 'response' });
  }

  update(cropCategoryMaster: ICropCategoryMaster): Observable<EntityResponseType> {
    return this.http.put<ICropCategoryMaster>(
      `${this.resourceUrl}/${this.getCropCategoryMasterIdentifier(cropCategoryMaster)}`,
      cropCategoryMaster,
      { observe: 'response' }
    );
  }

  partialUpdate(cropCategoryMaster: PartialUpdateCropCategoryMaster): Observable<EntityResponseType> {
    return this.http.patch<ICropCategoryMaster>(
      `${this.resourceUrl}/${this.getCropCategoryMasterIdentifier(cropCategoryMaster)}`,
      cropCategoryMaster,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICropCategoryMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICropCategoryMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCropCategoryMasterIdentifier(cropCategoryMaster: Pick<ICropCategoryMaster, 'id'>): number {
    return cropCategoryMaster.id;
  }

  compareCropCategoryMaster(o1: Pick<ICropCategoryMaster, 'id'> | null, o2: Pick<ICropCategoryMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getCropCategoryMasterIdentifier(o1) === this.getCropCategoryMasterIdentifier(o2) : o1 === o2;
  }

  addCropCategoryMasterToCollectionIfMissing<Type extends Pick<ICropCategoryMaster, 'id'>>(
    cropCategoryMasterCollection: Type[],
    ...cropCategoryMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cropCategoryMasters: Type[] = cropCategoryMastersToCheck.filter(isPresent);
    if (cropCategoryMasters.length > 0) {
      const cropCategoryMasterCollectionIdentifiers = cropCategoryMasterCollection.map(
        cropCategoryMasterItem => this.getCropCategoryMasterIdentifier(cropCategoryMasterItem)!
      );
      const cropCategoryMastersToAdd = cropCategoryMasters.filter(cropCategoryMasterItem => {
        const cropCategoryMasterIdentifier = this.getCropCategoryMasterIdentifier(cropCategoryMasterItem);
        if (cropCategoryMasterCollectionIdentifiers.includes(cropCategoryMasterIdentifier)) {
          return false;
        }
        cropCategoryMasterCollectionIdentifiers.push(cropCategoryMasterIdentifier);
        return true;
      });
      return [...cropCategoryMastersToAdd, ...cropCategoryMasterCollection];
    }
    return cropCategoryMasterCollection;
  }
}
