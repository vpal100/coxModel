from lifelines.datasets import load_rossi
from lifelines import CoxPHFitter
import json
from hashlib import sha256

def create_lifeline_model():
    rossi = load_rossi()
    cph = CoxPHFitter()
    cph.fit(rossi, 'week', 'arrest')
    return cph

def export_cox_model(cox_model):
    ret = dict()
    ret.update(cox_model.baseline_hazard_.to_dict())
    ret['parameters'] = cox_model.params_.to_dict()
    ret['mean'] = cox_model._norm_mean.to_dict()
    json_object = json.dumps(ret)
    with open(r"java\resources\cph_model_exports.json", 'w') as json_file:
        json_file.write(json_object)

    sha256_hash = sha256(json_object.encode('utf-8')).hexdigest()
    with open(r"java\resources\sha256.hash", 'w') as hash_file:
        hash_file.write(sha256_hash)

if __name__ == '__main__':
    cox_model = create_lifeline_model()
    export_cox_model(cox_model)

